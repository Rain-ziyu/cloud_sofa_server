package asia.huayu.controller;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryAdminDTO;
import asia.huayu.model.dto.CategoryOptionDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.CategoryVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static asia.huayu.constant.OptTypeConstant.DELETE;
import static asia.huayu.constant.OptTypeConstant.SAVE_OR_UPDATE;

@Tag(name = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @Operation(summary = "查看后台分类列表")
    @GetMapping("/categories")
    public Result<PageResultDTO<CategoryAdminDTO>> listCategoriesAdmin(ConditionVO conditionVO) {
        return Result.OK(categoryService.listCategoriesAdmin(conditionVO));
    }

    @Operation(summary = "搜索文章分类")
    @GetMapping("/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        return Result.OK(categoryService.listCategoriesBySearch(conditionVO));
    }

    @OptLog(optType = DELETE)
    @Operation(summary = "删除分类")
    @DeleteMapping("/categories")
    public Result<?> deleteCategories(@RequestBody List<Integer> categoryIds) {
        categoryService.deleteCategories(categoryIds);
        return Result.OK();
    }

    @OptLog(optType = SAVE_OR_UPDATE)
    @Operation(summary = "添加或修改分类")
    @PostMapping("/categories")
    public Result<?> saveOrUpdateCategory(@Valid @RequestBody CategoryVO categoryVO) {
        categoryService.saveOrUpdateCategory(categoryVO);
        return Result.OK();
    }


}
