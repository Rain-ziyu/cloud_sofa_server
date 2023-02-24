package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryDTO;
import asia.huayu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "获取所有分类")
    @GetMapping("/categories/all")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.OK(categoryService.listCategories());
    }

}
