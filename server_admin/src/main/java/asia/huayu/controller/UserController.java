package asia.huayu.controller;


import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.UserAdminDTO;
import asia.huayu.model.dto.UserAreaDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "用户账号模块")
@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @Operation(summary = "获取用户区域分布")
    @GetMapping("/users/area")
    public Result<List<UserAreaDTO>> listUserAreas(ConditionVO conditionVO) {
        return Result.OK(userService.listUserAreas(conditionVO));
    }

    @Operation(summary = "查询后台用户列表")
    @GetMapping("/users")
    public Result<PageResultDTO<UserAdminDTO>> listUsers(ConditionVO conditionVO) {
        return Result.OK(userService.listUsers(conditionVO));
    }


}
