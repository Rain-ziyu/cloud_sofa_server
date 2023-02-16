package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.LabelOptionDTO;
import asia.huayu.model.dto.MenuDTO;
import asia.huayu.model.dto.UserMenuDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.IsHiddenVO;
import asia.huayu.model.vo.MenuVO;
import asia.huayu.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Api(tags = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查看菜单列表")
    @GetMapping("/admin/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return Result.OK(menuService.listMenus(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @ApiOperation(value = "新增或修改菜单")
    @PostMapping("/admin/menus")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @ApiOperation(value = "修改目录是否隐藏")
    @PutMapping("/admin/menus/isHidden")
    public Result<?> updateMenuIsHidden(@RequestBody IsHiddenVO isHiddenVO) {
        menuService.updateMenuIsHidden(isHiddenVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/admin/menus/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.OK();
    }

    @ApiOperation(value = "查看角色菜单选项")
    @GetMapping("/admin/role/menus")
    public Result<List<LabelOptionDTO>> listMenuOptions() {
        return Result.OK(menuService.listMenuOptions());
    }

    @ApiOperation(value = "查看当前用户菜单")
    @GetMapping("/admin/user/menus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.OK(menuService.listUserMenus());
    }
}
