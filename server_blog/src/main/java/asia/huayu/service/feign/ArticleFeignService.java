package asia.huayu.service.feign;

import asia.huayu.common.entity.Result;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.service.fallback.ArticleFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

/**
 * @author RainZiYu
 * @Date 2023/3/9
 */
@FeignClient(value = "cloud-sofa-server-admin", fallback = ArticleFallbackService.class)
public interface ArticleFeignService {
    @PostMapping("/articles")
    Result<?> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO, @RequestHeader("token") String token);
}
