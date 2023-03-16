package asia.huayu.service;

import asia.huayu.entity.Article;
import asia.huayu.model.dto.ArticleAdminViewDTO;
import asia.huayu.model.dto.ArticleListDTO;
import asia.huayu.model.dto.PageResultDTO;
import asia.huayu.model.vo.ArticleTopFeaturedVO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.DeleteVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface ArticleService extends IService<Article> {
    PageResultDTO<ArticleListDTO> listArticlesAdmin(ConditionVO conditionVO);

    PageResultDTO<ArticleListDTO> listArticlesByUser(ConditionVO conditionVO) throws ExecutionException, InterruptedException;

    PageResultDTO<ArticleListDTO> listArticleById(List<Integer> articleIds, ConditionVO conditionVO);

    String saveOrUpdateArticle(ArticleVO articleVO);

    void updateArticleTopAndFeatured(ArticleTopFeaturedVO articleTopFeaturedVO);

    void updateArticleDelete(DeleteVO deleteVO);

    void deleteArticles(List<Integer> articleIds);

    ArticleAdminViewDTO getArticleByIdAdmin(Integer articleId);

    List<String> exportArticles(List<Integer> articleIdList);

}
