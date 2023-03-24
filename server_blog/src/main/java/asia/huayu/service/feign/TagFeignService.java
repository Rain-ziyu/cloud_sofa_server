package asia.huayu.service.feign;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.TagAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.fallback.TagFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/14
 */
@FeignClient(value = "cloud-sofa-server-admin", contextId = "tagFeignService", fallback = TagFallbackService.class)
public interface TagFeignService {
    /**
     * 方法listTagsAdminBySearch作用为：
     * 按照关键词搜索标签
     *
     * @param condition
     * @return asia.huayu.common.entity.Result<java.util.List < asia.huayu.model.dto.TagAdminDTO>>
     * @throws
     * @author RainZiYu
     */
    @GetMapping("/admin/tags/search")
    Result<List<TagAdminDTO>> listTagsBySearch(@SpringQueryMap ConditionVO condition, @RequestHeader String token);
}
