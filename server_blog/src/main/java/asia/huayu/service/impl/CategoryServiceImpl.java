package asia.huayu.service.impl;

import asia.huayu.entity.Category;
import asia.huayu.mapper.CategoryMapper;
import asia.huayu.model.dto.CategoryDTO;
import asia.huayu.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryDTO> listCategories() {
        return categoryMapper.listCategories();
    }

}
