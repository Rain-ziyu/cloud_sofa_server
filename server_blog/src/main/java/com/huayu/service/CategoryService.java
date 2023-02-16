package com.huayu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huayu.entity.Category;
import com.huayu.model.dto.CategoryAdminDTO;
import com.huayu.model.dto.CategoryDTO;
import com.huayu.model.dto.CategoryOptionDTO;
import com.huayu.model.dto.PageResultDTO;
import com.huayu.model.vo.CategoryVO;
import com.huayu.model.vo.ConditionVO;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
