package asia.huayu.service;

import asia.huayu.entity.Category;
import asia.huayu.model.dto.CategoryAdminDTO;
import asia.huayu.model.dto.CategoryOptionDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.CategoryVO;
import asia.huayu.model.vo.ConditionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    PageResultDTO<CategoryAdminDTO> listCategoriesAdmin(ConditionVO conditionVO);

    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVO conditionVO);

    void deleteCategories(List<Integer> categoryIds);

    void saveOrUpdateCategory(CategoryVO categoryVO);

}
