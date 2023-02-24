package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.dto.RoleDTO;
import asia.huayu.model.dto.UserRoleDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.RoleVO;
import asia.huayu.service.BlogRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Tag(name = "角色模块")
@RestController
public class RoleController {

    @Autowired
    private BlogRoleService blogRoleService;

    @Operation(summary = "查询用户角色选项")
    @GetMapping("/users/role")
    public Result<List<UserRoleDTO>> listUserRoles() {
        return Result.OK(blogRoleService.listUserRoles());
    }


    @Operation(summary = "查询角色列表")
    @GetMapping("/roles")
    public Result<PageResultDTO<RoleDTO>> listRoles(ConditionVO conditionVO) {

        return Result.OK(blogRoleService.listRoles(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "保存或更新角色")
    @PostMapping("/role")
    public Result<?> saveOrUpdateRole(@RequestBody @Valid RoleVO roleVO) {
        blogRoleService.saveOrUpdateRole(roleVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除角色")
    @DeleteMapping("/roles")
    public Result<?> deleteRoles(@RequestBody List<Integer> roleIdList) {
        blogRoleService.deleteRoles(roleIdList);
        return Result.OK();
    }
}
