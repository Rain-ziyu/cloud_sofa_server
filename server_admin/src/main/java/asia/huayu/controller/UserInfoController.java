package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserInfoDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.UserDisableVO;
import asia.huayu.model.vo.UserRoleVO;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.UPDATE;

@Tag(name = "用户信息模块")
@RestController
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;


    @OptLog(optType = UPDATE)
    @Operation(summary = "修改用户角色")
    @PutMapping("/users/role")
    public Result<?> updateUserRole(@Valid @RequestBody UserRoleVO userRoleVO) {
        userInfoService.updateUserRole(userRoleVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改用户禁用状态")
    @PutMapping("/users/disable")
    public Result<?> updateUserDisable(@Valid @RequestBody UserDisableVO userDisableVO) {
        userInfoService.updateUserDisable(userDisableVO);
        return Result.OK();
    }

    @Operation(summary = "查看在线用户")
    @GetMapping("/users/online")
    public Result<PageResultDTO<OnlineUser>> listOnlineUsers(ConditionVO conditionVO) {
        return Result.OK(userInfoService.listOnlineUsers(conditionVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "下线用户")
    @DeleteMapping("/users/{userInfoId}/online")
    public Result<?> removeOnlineUser(@PathVariable("userInfoId") Integer userInfoId) {
        userInfoService.removeOnlineUser(userInfoId);
        return Result.OK();
    }

    @Operation(summary = "根据token获取用户信息")
    @GetMapping("/users/info")
    public Result<UserInfoDTO> getUserInfo() {
        return Result.OK(userInfoService.getUserInfo());
    }
}
