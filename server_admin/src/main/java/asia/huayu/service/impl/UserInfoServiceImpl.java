package asia.huayu.service.impl;

import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.service.UserRoleService;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.dto.UserOnlineDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.UserDisableVO;
import asia.huayu.model.vo.UserRoleVO;
import asia.huayu.service.RedisService;
import asia.huayu.service.UserInfoService;
import asia.huayu.util.BeanCopyUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static asia.huayu.util.PageUtil.getLimitCurrent;
import static asia.huayu.util.PageUtil.getSize;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRoleService userRoleService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 每次传递进来是该用户所有的用户角色 每修改一次删除原有权限再新增
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getUserId()));
        List<UserRole> userRoleList = userRoleVO.getRoleIds().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(String.valueOf(roleId))
                        .userId(String.valueOf(userRoleVO.getUserId()))
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserDisable(UserDisableVO userDisableVO) {
        User user = User.builder()
                .id(userDisableVO.getId())
                .isDisable(userDisableVO.getIsDisable())
                .build();
        userMapper.updateById(user);
    }

    @Override
    public PageResultDTO<UserOnlineDTO> listOnlineUsers(ConditionVO conditionVO) {
        Map<String, Object> userMaps = redisService.hGetAll("login_user");
        Collection<Object> values = userMaps.values();
        ArrayList<UserDetailsDTO> userDetailsDTOs = new ArrayList<>();
        for (Object value : values) {
            userDetailsDTOs.add((UserDetailsDTO) value);
        }
        List<UserOnlineDTO> userOnlineDTOs = BeanCopyUtil.copyList(userDetailsDTOs, UserOnlineDTO.class);
        // TODO:使用es进行查询
        List<UserOnlineDTO> onlineUsers = userOnlineDTOs.stream()
                .filter(item -> StringUtils.isBlank(conditionVO.getKeywords()) || item.getNickname().contains(conditionVO.getKeywords()))
                .sorted(Comparator.comparing(UserOnlineDTO::getLastLoginTime).reversed())
                .collect(Collectors.toList());
        // 进行分页
        int fromIndex = getLimitCurrent().intValue();
        int size = getSize().intValue();
        int toIndex = onlineUsers.size() - fromIndex > size ? fromIndex + size : onlineUsers.size();
        List<UserOnlineDTO> userOnlineList = onlineUsers.subList(fromIndex, toIndex);
        return new PageResultDTO<>(userOnlineList, onlineUsers.size());
    }

    @Override
    public void removeOnlineUser(Integer userId) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        redisService.del(user.getUsername());
    }


    @Override
    public UserInfo getUserInfoByName(String username) {
        User user = userMapper.getUserByUsername(username);
        UserInfo userInfo = userInfoMapper.selectById(user.getId());
        return userInfo;
    }


}
