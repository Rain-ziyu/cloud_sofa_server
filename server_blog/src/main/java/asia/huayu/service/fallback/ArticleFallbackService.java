package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.model.dto.ArticleIdAndFilterDTO;
import asia.huayu.model.dto.ArticleListDTO;
import asia.huayu.model.dto.ArticleViewDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import asia.huayu.service.feign.ArticleFeignService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class ArticleFallbackService implements ArticleFeignService {

    @Override
    public Result<String> saveOrUpdateArticle(ArticleVO articleVO, String token) {
        return Result.ERROR("发布文章服务已下线");
    }

    @Override
    public Result<PageResultDTO<ArticleListDTO>> getArticlesCurrentUser(ConditionVO conditionVO, String token) {
        return Result.ERROR("加载文章列表已下线");
    }

    @Override
    public Result<String> updateArticleDelete(DeleteVO deleteVO, String token) {
        return Result.ERROR("更改文章删除状态已下线");
    }

    @Override
    public Result<ArticleViewDTO> getArticleBackById(Integer articleId, String token) {
        return Result.ERROR("用户发布文章已下线");
    }

    @Override
    public Result<String> saveArticleImages(MultipartFile file, String token) {
        return Result.ERROR("文章封面上传已下线");
    }

    @Override
    public Result<PageResultDTO<ArticleListDTO>> listArticlesById(ArticleIdAndFilterDTO articleIdAndFilterDTO, String token) {
        return Result.ERROR("根据文章id获取文章列表已下线");
    }

    @Override
    public Result<String> deleteArticles(List<Integer> articleIds, String token) {
        return Result.ERROR("永久删除文章功能已下线");
    }
}
