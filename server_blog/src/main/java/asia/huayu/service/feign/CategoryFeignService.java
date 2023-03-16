package asia.huayu.service.feign;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.CategoryOptionDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.fallback.ArticleFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/14
 */
@FeignClient(value = "cloud-sofa-server-admin", fallback = ArticleFallbackService.class)
public interface CategoryFeignService {
    /**
     * 方法listCategoriesAdminBySearch作用为：
     * 按照关键词搜索分类
     *
     * @param conditionVO
     * @return asia.huayu.common.entity.Result<java.util.List < asia.huayu.model.dto.CategoryOptionDTO>>
     * @throws
     * @author RainZiYu
     */
    @GetMapping("/admin/categories/search")
    Result<List<CategoryOptionDTO>> listCategoriesBySearch(@SpringQueryMap ConditionVO conditionVO, @RequestHeader String token);
}
