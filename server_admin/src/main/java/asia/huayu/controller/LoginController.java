package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.service.TokenService;
import asia.huayu.service.UserService;
import asia.huayu.util.SystemEnums;
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
            String systemToken = tokenService.getSystemToken();
            User createUser = userService.createUser(user, systemToken);
            return Result.OK(SystemEnums.ACCOUNT_CREATED_SUCCESSFULLY.VALUE, createUser);
        });
    }
}
