package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.service.TokenService;
import asia.huayu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    @Qualifier("userServiceImpl")
    private UserService userService;

    @PostMapping("/user")
    public Result createUser(@RequestBody @Valid User user) {
        return restProcessor(() -> {
            String systemToken = tokenService.getSystemToken();
            User createUser = userService.createUser(user, systemToken);
            return Result.OK(createUser);
        });
    }
}
