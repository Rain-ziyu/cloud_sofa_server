package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryDTO;
import asia.huayu.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "分类模块")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation("获取所有分类")
    @GetMapping("/categories/all")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.OK(categoryService.listCategories());
    }

}
