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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.*;

@Tag(name = "菜单模块")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Operation(summary = "查看菜单列表")
    @GetMapping("/menus")
    public Result<List<MenuDTO>> listMenus(ConditionVO conditionVO) {
        return Result.OK(menuService.listMenus(conditionVO));
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "新增或修改菜单")
    @PostMapping("/menus")
    public Result<?> saveOrUpdateMenu(@Valid @RequestBody MenuVO menuVO) {
        menuService.saveOrUpdateMenu(menuVO);
        return Result.OK();
    }

    @OptLog(optType = UPDATE)
    @Operation(summary = "修改目录是否隐藏")
    @PutMapping("/menus/isHidden")
    public Result<?> updateMenuIsHidden(@RequestBody IsHiddenVO isHiddenVO) {
        menuService.updateMenuIsHidden(isHiddenVO);
        return Result.OK();
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除菜单")
    @DeleteMapping("/menus/{menuId}")
    public Result<?> deleteMenu(@PathVariable("menuId") Integer menuId) {
        menuService.deleteMenu(menuId);
        return Result.OK();
    }

    @Operation(summary = "查看所有菜单选项")
    @GetMapping("/role/menus")
    public Result<List<LabelOptionDTO>> listMenuOptions() {
        return Result.OK(menuService.listMenuOptions());
    }

    @Operation(summary = "查看当前用户菜单")
    @GetMapping("/user/menus")
    public Result<List<UserMenuDTO>> listUserMenus() {
        return Result.OK(menuService.listUserMenus());
    }
}
