package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryOptionDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.feign.CategoryFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class CategoryFallbackService implements CategoryFeignService {


    /**
     * 方法listCategoriesAdminBySearch作用为：
     * 按照关键词搜索分类
     *
     * @param conditionVO
     * @return asia.huayu.common.entity.Result<java.util.List < asia.huayu.model.dto.CategoryOptionDTO>>
     * @throws
     * @author RainZiYu
     */
    @Override
    public Result<List<CategoryOptionDTO>> listCategoriesBySearch(ConditionVO conditionVO, String token) {
        return Result.OK("分类关键词查询已下线");
    }
}
