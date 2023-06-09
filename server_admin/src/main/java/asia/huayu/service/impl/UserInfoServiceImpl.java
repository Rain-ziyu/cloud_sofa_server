package asia.huayu.service.impl;

import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.service.RoleService;
import asia.huayu.auth.service.UserRoleService;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.UserDisableVO;
import asia.huayu.model.vo.UserRoleVO;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.RedisService;
import asia.huayu.service.UserInfoService;
import asia.huayu.util.UserUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    private RoleService roleService;
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserRoleService userRoleService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserRole(UserRoleVO userRoleVO) {
        // 每次传递进来是该用户所有的用户角色 每修改一次删除原有权限再新增
        userRoleService.remove(new LambdaQueryWrapper<UserRole>()
                .eq(UserRole::getUserId, userRoleVO.getId()));
        List<UserRole> userRoleList = userRoleVO.getRoleIds().stream()
                .map(roleId -> UserRole.builder()
                        .roleId(String.valueOf(roleId))
                        .userId(String.valueOf(userRoleVO.getId()))
                        .build())
                .collect(Collectors.toList());
        userRoleService.saveBatch(userRoleList);
        //     更新redis中该用户的角色信息
        User user = userMapper.selectById(userRoleVO.getId());
        // 如果该用户处于登录状态 更新redis
        if (redisService.hasKey(user.getUsername())) {
            List<String> roles = roleService.selectRoleByUserId(String.valueOf(user.getId()));
            redisService.set(user.getUsername(), roles);
        }
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
    public PageResultDTO<OnlineUser> listOnlineUsers(ConditionVO conditionVO) {
        // 将用户信息存从redis中取出
        Map<String, Object> userMaps = redisService.hGetAll(SystemValue.LOGIN_USER);
        Collection<Object> values = userMaps.values();
        ArrayList<OnlineUser> onlineUsers = new ArrayList<>();
        for (Object value : values) {
            OnlineUser onlineUser = (OnlineUser) value;
            // 如果当前时间小于过期时间
            if (DateUtil.compare(onlineUser.getExpireTime(), new Date()) > 0) {
                onlineUsers.add(onlineUser);
            }
        }


        List<OnlineUser> onlineUserList = onlineUsers.stream()
                .filter(item -> StringUtils.isBlank(conditionVO.getKeywords()) || item.getName().contains(conditionVO.getKeywords()))
                .sorted(Comparator.comparing(OnlineUser::getExpireTime).reversed())
                .collect(Collectors.toList());
        // 进行分页
        int fromIndex = getLimitCurrent().intValue();
        int size = getSize().intValue();
        int toIndex = onlineUsers.size() - fromIndex > size ? fromIndex + size : onlineUsers.size();
        List<OnlineUser> userOnlineList = onlineUserList.subList(fromIndex, toIndex);
        return new PageResultDTO<>(userOnlineList, onlineUsers.size());
    }

    @Autowired
    TokenManager tokenManager;

    @Override
    public void removeOnlineUser(String userToken) {
        String userInfoFromToken = tokenManager.getUserInfoFromToken(userToken);
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userInfoFromToken));
        // redisService.del(SystemValue.ONLINE_USER_AUTH + user.getUsername());
        // 获取登陆的用户信息
        OnlineUser onlineUser = (OnlineUser) redisService.hGet(SystemValue.LOGIN_USER, userToken);
        // 删除redis中登录用户信息
        redisService.hDel(SystemValue.LOGIN_USER, userToken);
        // 删除redis中所统计的用户ip
        redisService.hIncr(SystemValue.USER_AREA, IpUtil.getIpProvince(onlineUser.getIpSource()), -1L);
    }


    @Override
    public UserInfo getUserInfoByName(String username) {
        User user = userMapper.getUserByUsername(username);
        UserInfo userInfo = userInfoMapper.selectById(user.getId());
        return userInfo;
    }

    @Override
    public UserInfoDTO getUserInfo() {
        User userByUsername = userMapper.getUserByUsername(UserUtil.getAuthentication().getName());
        UserInfoDTO userInfo = userInfoMapper.selectDTOById(userByUsername.getId());
        HttpServletRequest request = RequestUtil.getRequest();
        // 不同步返回用户的token和refreshToken
        // userInfo.setToken(request.getParameter("token"));
        // userInfo.setRefreshToken(request.getParameter("refreshToken"));
        return userInfo;
    }


}
