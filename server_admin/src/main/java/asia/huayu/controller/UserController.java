package asia.huayu.controller;


import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.constant.ReturnMessageConstant;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserAdminDTO;
import asia.huayu.model.dto.UserAreaDTO;
import asia.huayu.model.dto.UserDetailsDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.QQLoginVO;
import asia.huayu.model.vo.UserVO;
import asia.huayu.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Api(tags = "用户账号模块")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "发送邮箱验证码")
    @ApiImplicitParam(name = "userEmail", value = "邮箱", required = true, dataType = "String")
    @GetMapping("/users/code")
    public Result<?> sendCode(String userEmail) {
        userService.sendCode(userEmail);
        return Result.OK(ReturnMessageConstant.SEND_VERIFY_SUCCESS);
    }

    @ApiOperation(value = "获取用户区域分布")
    @GetMapping("/admin/users/area")
    public Result<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return Result.OK(userService.listUserAreas(conditionVO));
    }

    @ApiOperation(value = "查询后台用户列表")
    @GetMapping("/admin/users")
    public Result<PageResultDTO<UserAdminDTO>> listUsers(ConditionVO conditionVO) {
        return Result.OK(userService.listUsers(conditionVO));
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/users/register")
    public Result<?> register(@Valid @RequestBody UserVO userVO) {
        userService.register(userVO);
        return Result.OK("用户注册成功");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改密码")
    @PutMapping("/users/password")
    public Result<?> updatePassword(@Valid @RequestBody UserVO user) {
        userService.updatePassword(user);
        return Result.OK("修改密码成功");
    }


    @ApiOperation(value = "qq登录")
    @PostMapping("/users/oauth/qq")
    public Result<UserDetailsDTO> qqLogin(@Valid @RequestBody QQLoginVO qqLoginVO) {
        return Result.OK(userService.qqLogin(qqLoginVO));
    }

}
