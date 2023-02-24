package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.security.entity.TokenDTO;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
