package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RainZiYu
 * @Date 2023/1/13
 */
@RestController
@CrossOrigin
public class LoginController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public Result createUser(@RequestBody User user) {
        return restProcessor(() -> {
            User createUser = userService.createUser(user);
            return Result.OK(createUser);
        });
    }
}
