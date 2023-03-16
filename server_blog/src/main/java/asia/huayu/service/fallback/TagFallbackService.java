package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.TagAdminDTO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.service.feign.TagFeignService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class TagFallbackService implements TagFeignService {


    /**
     * 方法listTagsAdminBySearch作用为：
     * 按照关键词搜索标签
     *
     * @param condition
     * @return asia.huayu.common.entity.Result<java.util.List < asia.huayu.model.dto.TagAdminDTO>>
     * @throws
     * @author RainZiYu
     */
    @Override
    public Result<List<TagAdminDTO>> listTagsBySearch(ConditionVO condition, String token) {
        return Result.ERROR("通过关键词检索标签已下线");
    }
}
