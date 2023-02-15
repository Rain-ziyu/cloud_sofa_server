package asia.huayu.controller;

import asia.huayu.auth.entity.Permission;
import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.service.MenuRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/2/10
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    MenuRoleService menuRoleService;

    @ApiOperation(value = "获取全部菜单")
    @GetMapping
    public Result getAllMenu() {
        return restProcessor(() -> {
            List<Permission> permissions = menuRoleService.getAllMenuRole();
            return Result.OK("获取菜单列表成功", permissions);
        });
    }

}
