package asia.huayu.controller;


import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.constant.ReturnMessageConstant;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.vo.QQLoginVO;
import asia.huayu.model.vo.UserVO;
import asia.huayu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Tag(name = "用户账号模块")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "发送邮箱验证码")
    @Parameter(name = "username", description = "邮箱", required = true)
    @GetMapping("/users/code")
    public Result<?> sendCode(String username) {
        userService.sendCode(username);
        return Result.OK(ReturnMessageConstant.SEND_VERIFY_SUCCESS);
    }


    @Operation(summary = "用户注册")
    @PostMapping("/users/register")
    public Result<?> register(@Valid @RequestBody UserVO userVO) {
        userService.register(userVO);
        return Result.OK("用户注册成功");
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改密码")
    @PutMapping("/users/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userService.updatePassword(user);
        return Result.OK("修改密码成功");
    }


    @Operation(summary = "qq登录")
    @PostMapping("/users/oauth/qq")
    public Result<UserDetailsDTO> qqLogin(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return Result.OK(userService.qqLogin(qqLoginVO));
    }

}
