package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.entity.User;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.entity.TokenDTO;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.service.UserLoginInfoService;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.TokenService;
import asia.huayu.service.UserService;
import cn.hutool.core.date.DateUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author RainZiYu
 * @Date 2023/1/13
 */
@RestController
@CrossOrigin
@Slf4j
@Tag(name = "refreshToken刷新")
public class LoginController extends BaseController {
    @Autowired
    TokenService tokenService;
    @Autowired
    TokenManager tokenManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    @Autowired
    UserLoginInfoService userLoginInfoService;

    @PostMapping("/refreshToken")
    public Result refreshToken(@RequestBody @NotBlank(message = "refreshToken不能为空") String refreshToken) {
        return restProcessor(() -> {
            String userInfoFromRefreshToken = tokenManager.getUserInfoFromRefreshToken(refreshToken);
            HttpServletRequest request = RequestUtil.getRequest();

            TokenDTO tokenDTO = new TokenDTO();
            OnlineUser onlineUser = new OnlineUser();
            User user = userService.getUserByUsername(userInfoFromRefreshToken);
            onlineUser.setUserId(user.getId());
            // 存储登陆时间为当前时间+时间偏移 方便获取的时候比较
            onlineUser.setExpireTime(DateUtil.offsetMillisecond(new Date(), Math.toIntExact(SystemValue.TOKEN_EXPIRATION_TIME)));
            // 刷新token自动登录
            onlineUser.setLoginType(3);
            String ipAddress = IpUtil.getIpAddress(request);
            onlineUser.setIpAddress(ipAddress);
            String ipSource = IpUtil.getIpSource(ipAddress);
            onlineUser.setIpSource(ipSource);
            onlineUser.setBrowser(IpUtil.getUserAgent(request).getBrowser().getName());
            onlineUser.setOs(IpUtil.getUserAgent(request).getOperatingSystem().getName());
            onlineUser.setName(user.getUsername());
            userLoginInfoService.addLoginInfo(onlineUser);
            String token = tokenManager.createToken(userInfoFromRefreshToken, onlineUser);
            tokenDTO.setExpires(new Date(System.currentTimeMillis() + SystemValue.TOKEN_EXPIRATION_TIME));
            String newRefreshToken = tokenManager.createRefreshToken(userInfoFromRefreshToken);
            tokenDTO.setRefreshToken(newRefreshToken);
            tokenDTO.setToken(token);
            // 刷新redis中用户权限的时间
            redisTemplate.expire(userInfoFromRefreshToken, SystemValue.TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
            return Result.OK(tokenDTO);
        });

    }
}
