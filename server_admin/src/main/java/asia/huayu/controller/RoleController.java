package asia.huayu.controller;

import asia.huayu.auth.entity.Role;
import asia.huayu.auth.service.RoleService;
import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.entity.User;
import asia.huayu.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取角色分页列表")
    @GetMapping("/page")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            Long limit) {
        Page<Role> pageParam = new Page<>(page, limit);
        roleService.page(pageParam);
        return Result.OK(page);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.OK();
    }

    @ApiOperation(value = "获取当前用户角色")
    @GetMapping
    public Result getCurrentUser() {
        return restProcessor(() -> {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userService.selectUser(userName);
            Map<String, Object> roleByUserId = roleService.findRoleByUserId(user.getId());
            return Result.OK(roleByUserId);
        });
    }
}

