package asia.huayu.controller;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryDTO;
import asia.huayu.model.dto.CategoryOptionDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.CategoryService;
import asia.huayu.service.TokenService;
import asia.huayu.service.feign.CategoryFeignService;
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
    @Autowired
    private CategoryFeignService categoryFeignService;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "获取所有分类")
    @GetMapping("/categories/all")
    public Result<List<CategoryDTO>> listCategories() {
        return Result.OK(categoryService.listCategories());
    }

    /**
     * 方法searchCategories作用为：
     * 按照关键词查询
     *
     * @param
     * @return asia.huayu.common.entity.Result<java.util.List < asia.huayu.model.dto.CategoryDTO>>
     * @throws
     * @author RainZiYu
     */
    @GetMapping("/categories/search")
    public Result<List<CategoryOptionDTO>> listCategoriesAdminBySearch(ConditionVO conditionVO) {
        String token = tokenService.getUserTokenOrSystemToken();
        return Result.OK(categoryFeignService.listCategoriesBySearch(conditionVO, token).getData());
    }
}
