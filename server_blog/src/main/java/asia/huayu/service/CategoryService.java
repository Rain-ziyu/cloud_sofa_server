package asia.huayu.service;

import asia.huayu.entity.Category;
import asia.huayu.model.dto.CategoryDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<CategoryDTO> listCategories();


}
