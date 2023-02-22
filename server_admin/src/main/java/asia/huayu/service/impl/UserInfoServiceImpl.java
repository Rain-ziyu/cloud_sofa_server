package asia.huayu.service.impl;

import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.service.UserRoleService;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.constant.RedisConstant;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.enums.FilePathEnum;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.dto.UserOnlineDTO;
import asia.huayu.model.vo.*;
import asia.huayu.service.RedisService;
import asia.huayu.service.UserInfoService;
import asia.huayu.strategy.context.UploadStrategyContext;
import asia.huayu.util.BeanCopyUtil;
import asia.huayu.util.UserUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private RedisService redisService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UploadStrategyContext uploadStrategyContext;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(UserInfoVO userInfoVO) {
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .nickname(userInfoVO.getNickname())
                .intro(userInfoVO.getIntro())
                .website(userInfoVO.getWebsite())
                .build();
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public String updateUserAvatar(MultipartFile file) {
        String avatar = uploadStrategyContext.executeUploadStrategy(file, FilePathEnum.AVATAR.getPath());
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .avatar(avatar)
                .build();
        userInfoMapper.updateById(userInfo);
        return avatar;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveUserEmail(EmailVO emailVO) {
        if (Objects.isNull(redisService.get(RedisConstant.USER_CODE_KEY + emailVO.getEmail()))) {
            throw new ServiceProcessException("验证码错误");
        }
        if (!emailVO.getCode().equals(redisService.get(RedisConstant.USER_CODE_KEY + emailVO.getEmail()).toString())) {
            throw new ServiceProcessException("验证码错误！");
        }
        String name = UserUtil.getAuthentication().getName();
        User user = userMapper.getUserByUsername(name);
        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .email(emailVO.getEmail())
                .build();
        userInfoMapper.updateById(userInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserSubscribe(SubscribeVO subscribeVO) {
        UserInfo temp = userInfoMapper.selectOne(new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getId, subscribeVO.getUserId()));
        if (StringUtils.isEmpty(temp.getEmail())) {
            throw new ServiceProcessException("邮箱未绑定！");
        }
        UserInfo userInfo = UserInfo.builder()
                .id(subscribeVO.getUserId())
                .isSubscribe(subscribeVO.getIsSubscribe())
                .build();
        userInfoMapper.updateById(userInfo);
    }

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
    public UserInfoDTO getUserInfoById(Integer id) {
        UserInfo userInfo = userInfoMapper.selectById(id);
        // TODO: 查询对应的ip地址等信息
        return BeanCopyUtil.copyObject(userInfo, UserInfoDTO.class);
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

        userInfo.setToken(request.getParameter("token"));
        userInfo.setRefreshToken(request.getParameter("refreshToken"));
        return userInfo;
    }

}
