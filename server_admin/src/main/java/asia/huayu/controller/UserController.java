package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.service.UserService;
import asia.huayu.util.SystemEnums;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * @author RainZiYu
 * @Date 2023/2/8
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    /**
     * 方法getCurrentUserInfo作用为：
     * 获取当前用户信息
     *
     * @param
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */
    @GetMapping
    public Result getCurrentUserInfo() {
        return restProcessor(() -> {
            String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.selectUser(currentUserName);
            if (user != null) {
                return Result.OK(SystemEnums.LOGIN_SUCCESS.VALUE, user);
            } else {
                return Result.ERROR(SystemEnums.ACCOUNT_DOES_NOT_EXIST.VALUE);
            }
        });
    }

    /**
     * 方法updateUserInfo作用为：
     * 更新用户匿名以及用户签名
     *
     * @param user
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */
    @PutMapping
    public Result updateUserInfo(@RequestBody User user) {
        return restProcessor(() -> {
            // 获取当前登陆用户的用户名
            user.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            // 设置用户名
            return Result.OK(userService.updateUserInfoByUserName(user));
        });
    }

    /**
     * 方法deleteUser作用为：
     * 用户注销自己的账户
     *
     * @param
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */
    @DeleteMapping
    public Result deleteUser() {
        return restProcessor(() -> {
            // 获取当前登陆用户的用户名
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Integer count = userService.deleteUserByUserName(name);
            return Result.OK(SystemEnums.LOG_OFF_SUCCESSFULLY.VALUE);
        });
    }

    @GetMapping("/list")
    public Result getUserList(int page, int pageSize) {
        return restProcessor(() -> {
            Page<User> userPage = new Page<>(page, pageSize);
            IPage<User> userIPage = userService.selectUserListByPage(userPage);
            return Result.OK(userIPage);
        });
    }

    @GetMapping("/fuzzy")
    public Result getUserFuzzy(String userName, int page, int pageSize) {
        return restProcessor(() -> {
            IPage<User> userIPage = userService.selectUserFuzzy(userName, page, pageSize);
            return Result.OK(userIPage);
        });
    }

}
