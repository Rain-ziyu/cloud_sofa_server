package asia.huayu.strategy.impl.login;

import asia.huayu.auth.entity.UserRole;
import asia.huayu.auth.mapper.UserRoleMapper;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.entity.User;
import asia.huayu.entity.UserInfo;
import asia.huayu.entity.UserLoginInfo;
import asia.huayu.enums.RoleEnum;
import asia.huayu.mapper.UserInfoMapper;
import asia.huayu.mapper.UserMapper;
import asia.huayu.model.dto.SocialTokenDTO;
import asia.huayu.model.dto.SocialUserInfoDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.entity.SecurityUser;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.RedisService;
import asia.huayu.service.impl.UserLoginInfoServiceImpl;
import asia.huayu.strategy.SocialLoginStrategy;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

import static asia.huayu.constant.CommonConstant.TRUE;

@Service
public abstract class AbstractSocialLoginStrategyImpl implements SocialLoginStrategy {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private TokenManager tokenManager;
    // 直接注入实现类 不然看不到save方法
    @Autowired
    private UserLoginInfoServiceImpl userLoginInfoService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public UserDetailsDTO login(String data) {
        UserDetailsDTO userDetailsDTO;
        SocialTokenDTO socialTokenDTO = getSocialToken(data);
        HttpServletRequest request = RequestUtil.getRequest();
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, socialTokenDTO.getOpenId())
                .eq(User::getRegisterType, socialTokenDTO.getLoginType()));
        // 如果用户信息已存在则登录 不存在则创建账户
        if (Objects.nonNull(user)) {
            userDetailsDTO = setUserLoginInfo(user);
        } else {
            userDetailsDTO = createUser(socialTokenDTO);
        }
        if (userDetailsDTO.getUser().getIsDisable().equals(TRUE)) {
            throw new ServiceProcessException("用户帐号已被锁定");
        }
        // TODO: 使用tokenManager 为集成登录创建token
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setUserId(user.getId());
        // 存储登陆时间为当前时间+时间偏移 方便获取的时候比较
        onlineUser.setExpireTime(DateUtil.offsetMillisecond(new Date(), Math.toIntExact(SystemValue.TOKEN_EXPIRATION_TIME)));
        // 集成登录
        onlineUser.setLoginType(2);
        String ipAddress = IpUtil.getIpAddress(request);
        onlineUser.setIpAddress(ipAddress);
        String ipSource = IpUtil.getIpSource(ipAddress);
        onlineUser.setIpSource(ipSource);
        onlineUser.setBrowser(IpUtil.getUserAgent(request).getBrowser().getName());
        onlineUser.setOs(IpUtil.getUserAgent(request).getOperatingSystem().getName());
        onlineUser.setName(user.getUsername());
        userLoginInfoService.addLoginInfo(onlineUser);
        String token = tokenManager.createToken(user.getUsername(), onlineUser);
        String refreshToken = tokenManager.createRefreshToken(user.getUsername());
        userDetailsDTO.setToken(token);
        userDetailsDTO.setRefreshToken(refreshToken);
        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(user.getUsername());
        // redis中放入该用户的权限信息
        redisService.set(user.getUsername(), userDetails.getAuthorities());
        return userDetailsDTO;
    }

    public abstract SocialTokenDTO getSocialToken(String data);

    public abstract SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO);


    private UserDetailsDTO setUserLoginInfo(User user) {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(user.getId());
        userLoginInfo.setLoginType(user.getRegisterType());
        HttpServletRequest request = RequestUtil.getRequest();
        createUserLoginInfo(request, userLoginInfo);
        UserInfo userInfo = userInfoMapper.selectById(user.getId());
        return UserDetailsDTO.builder().user(user).userInfo(userInfo).userLoginInfo(userLoginInfo).build();
    }

    private UserDetailsDTO createUser(SocialTokenDTO socialToken) {
        HttpServletRequest request = RequestUtil.getRequest();
        // 获取 第三方登录的用户信息
        SocialUserInfoDTO socialUserInfo = getSocialUserInfo(socialToken);
        UserInfo userInfo = UserInfo.builder()
                .nickname(socialUserInfo.getNickname())
                .avatar(socialUserInfo.getAvatar())
                .build();
        // 插入用户信息
        userInfoMapper.insert(userInfo);
        User user = User.builder()
                .id(userInfo.getId())
                .username(socialToken.getOpenId())
                .password(socialToken.getAccessToken())
                .registerType(socialToken.getLoginType())
                .build();
        // 插入用户表
        userMapper.insert(user);
        // 插入用户登陆记录表
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(user.getId());
        userLoginInfo.setLoginType(user.getRegisterType());
        createUserLoginInfo(request, userLoginInfo);
        UserRole userRole = UserRole.builder()
                .userId(String.valueOf(userInfo.getId()))
                .roleId(String.valueOf(RoleEnum.USER.getRoleId()))
                .build();
        // 插入角色
        userRoleMapper.insert(userRole);
        return UserDetailsDTO.builder().user(user).userInfo(userInfo).userLoginInfo(userLoginInfo).build();
    }

    private void createUserLoginInfo(HttpServletRequest request, UserLoginInfo userLoginInfo) {
        UserAgent userAgent = IpUtil.getUserAgent(request);
        userLoginInfo.setBrowser(userAgent.getBrowser().getName());
        userLoginInfo.setOs(userAgent.getOperatingSystem().getName());
        String ipAddress = IpUtil.getIpAddress(request);
        userLoginInfo.setIpAddress(ipAddress);
        userLoginInfo.setIpSource(IpUtil.getIpSource(ipAddress));
        userLoginInfo.setLoginTime(DateUtil.date());
        userLoginInfoService.save(userLoginInfo);
    }

}
