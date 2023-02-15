package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.security.entity.TokenDTO;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.TokenService;
import asia.huayu.service.UserService;
import asia.huayu.util.SystemEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
public class LoginController extends BaseController {
    @Autowired
    TokenService tokenService;
    @Autowired
    TokenManager tokenManager;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    /**
     * 方法registerUser作用为：
     * 注册新用户
     *
     * @param user
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */
    @PostMapping("/register")
    public Result registerUser(@RequestBody @Valid User user) {
        return restProcessor(() -> {
            // 生成系统内部token用于初始化用户
            String systemToken = tokenService.getSystemToken();
            User createUser = userService.createUser(user, systemToken);
            return Result.OK(SystemEnums.ACCOUNT_CREATED_SUCCESSFULLY.name(), createUser);
        });
    }

    @PostMapping("/refreshToken")
    public Result refreshToken(@RequestBody @NotBlank(message = "refreshToken不能为空") String refreshToken) {
        return restProcessor(() -> {
            String userInfoFromRefreshToken = tokenManager.getUserInfoFromRefreshToken(refreshToken);
            TokenDTO tokenDTO = new TokenDTO();
            String token = tokenManager.createToken(userInfoFromRefreshToken);
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
