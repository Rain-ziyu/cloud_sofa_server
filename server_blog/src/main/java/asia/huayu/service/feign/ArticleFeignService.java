package asia.huayu.service.feign;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.ArticleIdAndFilterDTO;
import asia.huayu.model.dto.ArticleListDTO;
import asia.huayu.model.dto.ArticleViewDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import asia.huayu.service.fallback.ArticleFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/3/9
 */
@FeignClient(value = "cloud-sofa-server-admin", fallback = ArticleFallbackService.class)
public interface ArticleFeignService {
    @PostMapping("/admin/articles")
    Result<String> saveOrUpdateArticle(@Valid @RequestBody ArticleVO articleVO, @RequestHeader("token") String token);


    @GetMapping("/admin/articles/byUser")
    Result<PageResultDTO<ArticleListDTO>> getArticlesCurrentUser(@RequestParam ConditionVO conditionVO, @RequestHeader("token") String token);

    /**
     * 方法updateArticleDelete作用为：
     * 不主动携带交由FeignBuilder统一添加
     *
     * @param deleteVO
     * @return asia.huayu.common.entity.Result<?>
     * @throws
     * @author RainZiYu
     */
    @PutMapping("/admin/articles")
    Result<String> updateArticleDelete(@Valid @RequestBody DeleteVO deleteVO, @RequestHeader("token") String token);

    @GetMapping("/admin/articles/{articleId}")
    Result<ArticleViewDTO> getArticleBackById(@PathVariable("articleId") Integer articleId, @RequestHeader("token") String token);

    @PostMapping(value = "/admin/articles/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> saveArticleImages(@RequestPart("file") MultipartFile file, @RequestHeader("token") String token);

    @PostMapping("/admin/articles/byId")
    Result<PageResultDTO<ArticleListDTO>> listArticlesById(@RequestBody ArticleIdAndFilterDTO articleIdAndFilterDTO, @RequestHeader("token") String token);

    @DeleteMapping("/admin/articles/delete")
    Result<String> deleteArticles(@RequestBody List<Integer> articleIds, @RequestHeader("token") String token);

    @PostMapping("/admin/articles/export")
    Result<List<String>> exportArticles(@RequestBody List<Integer> articleIds, @RequestHeader("token") String token);

    @PostMapping(value = "/admin/articles/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<String> importArticles(@RequestPart("file") MultipartFile file, @RequestParam(required = false) String type, @RequestHeader("token") String token);
}
