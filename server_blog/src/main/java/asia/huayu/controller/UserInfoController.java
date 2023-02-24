package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.EmailVO;
import asia.huayu.model.vo.SubscribeVO;
import asia.huayu.model.vo.UserInfoVO;
import asia.huayu.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Tag(name = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @OptLog(optType = UPDATE)
    @Operation(summary = "更新用户信息")
    @PutMapping("/users/info")
    public Result<?> updateUserInfo(@Valid @RequestBody UserInfoVO userInfoVO) {
        userInfoService.updateUserInfo(userInfoVO);
        return Result.OK("用户信息更新成功");
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "更新用户头像")
    @Parameter(name = "file", description = "用户头像", required = true)
    @PostMapping("/users/avatar")
    public Result<String> updateUserAvatar(MultipartFile file) {
        return Result.OK("更新用户头像成功", userInfoService.updateUserAvatar(file));
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "绑定用户邮箱")
    @PutMapping("/users/email")
    public Result<?> saveUserEmail(@Valid @RequestBody EmailVO emailVO) {
        userInfoService.saveUserEmail(emailVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改用户的订阅状态")
    @PutMapping("/users/subscribe")
    public Result<?> updateUserSubscribe(@RequestBody SubscribeVO subscribeVO) {
        userInfoService.updateUserSubscribe(subscribeVO);
        return Result.OK("用户更新订阅状态成功");
    }


    @Operation(summary = "根据id获取用户信息")
    @GetMapping("/users/info/{userInfoId}")
    public Result<UserInfoDTO> getUserInfoById(@PathVariable("userInfoId") Integer userInfoId) {
        return Result.OK(userInfoService.getUserInfoById(userInfoId));
    }

    @Operation(summary = "根据token获取用户信息")
    @GetMapping("/users/info")
    public Result<UserInfoDTO> getUserInfo() {
        return Result.OK(userInfoService.getUserInfo());
    }
}
