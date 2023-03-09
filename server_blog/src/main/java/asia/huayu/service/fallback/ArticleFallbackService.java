package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.service.feign.ArticleFeignService;
import org.springframework.stereotype.Component;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class ArticleFallbackService implements ArticleFeignService {

    @Override
    public Result<?> saveOrUpdateArticle(ArticleVO articleVO, String token) {
        return Result.ERROR("发布文章服务已下线");
    }
}
