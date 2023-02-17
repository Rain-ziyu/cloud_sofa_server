package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.dto.UserOnlineDTO;
import asia.huayu.model.vo.*;
import asia.huayu.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Api(tags = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @OptLog(optType = UPDATE)
    @ApiOperation("更新用户信息")
    @PutMapping("/users/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.OK("用户信息更新成功");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("更新用户头像")
    @ApiImplicitParam(name = "file", value = "用户头像", required = true, dataType = "MultipartFile")
    @PostMapping("/users/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.OK(userInfoService.updateUserAvatar(file));
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("绑定用户邮箱")
    @PutMapping("/users/email")
    public Result<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation("修改用户的订阅状态")
    @PutMapping("/users/subscribe")
    public Result<?> updateUserSubscribe(@RequestBody SubscribeVO subscribeVO) {
        userInfoService.updateUserSubscribe(subscribeVO);
        return Result.OK("用户更新订阅状态成功");
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户角色")
    @PutMapping("/admin/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改用户禁用状态")
    @PutMapping("/admin/users/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.OK();
    }

    @ApiOperation(value = "查看在线用户")
    @GetMapping("/admin/users/online")
    public Result<PageResultDTO<UserOnlineDTO>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.OK(userInfoService.listOnlineUsers(conditionVO));
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "下线用户")
    @DeleteMapping("/admin/users/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.OK();
    }

    @ApiOperation("根据id获取用户信息")
    @GetMapping("/users/info/{userInfoId}")
    public Result<UserInfoDTO> getUserInfoById(@PathVariable("userInfoId") Integer userInfoId) {
        return Result.OK(userInfoService.getUserInfoById(userInfoId));
    }

}
