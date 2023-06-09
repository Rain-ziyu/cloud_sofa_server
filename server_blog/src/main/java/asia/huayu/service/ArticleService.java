package asia.huayu.service;

import asia.huayu.common.entity.Result;
import asia.huayu.entity.Article;
import asia.huayu.model.dto.*;
import asia.huayu.model.vo.ArticlePasswordVO;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.model.vo.ConditionVO;
import asia.huayu.model.vo.TempDeleteVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ArticleService extends IService<Article> {

    TopAndFeaturedArticlesDTO listTopAndFeaturedArticles();

    PageResultDTO<ArticleCardDTO> listArticles();

    PageResultDTO<ArticleCardDTO> listArticlesByCategoryId(Integer categoryId);

    ArticleDTO getArticleById(Integer articleId);

    void accessArticle(ArticlePasswordVO articlePasswordVO);

    PageResultDTO<ArticleCardDTO> listArticlesByTagId(Integer tagId);

    PageResultDTO<ArchiveDTO> listArchives();

    @Transactional(rollbackFor = Exception.class)
    Result saveOrUpdateArticle(ArticleVO articleVO);

    /**
     * 方法listArchivesByUser作用为：
     * 获取该用户发表的文章
     *
     * @param
     * @return asia.huayu.model.dto.PageResultDTO<asia.huayu.model.dto.ArchiveDTO>
     * @throws
     * @author RainZiYu
     */
    PageResultDTO<ArticleListDTO> listArticlesByUser(ConditionVO conditionVO);

    List<ArticleSearchDTO> listArticlesBySearch(ConditionVO condition);


    PageResultDTO listArticlesByTempId(List<Long> tempArticleIds, ConditionVO conditionVO);

    Result<ArticleViewDTO> getArticleBackById(Long articleId);

    String updateArticleDelete(TempDeleteVO tempDeleteVO);

    String deleteArticles(List<Long> articleIds);

    /**
     * 方法exportArticles作用为：
     * 导出文章，返回给前端文件下载地址
     *
     * @param articleIds
     * @return java.util.List<java.lang.String>
     * @throws
     * @author RainZiYu
     */
    Result<List<String>> exportArticles(List<Long> articleIds);

    Result importArticles(MultipartFile file, String type);
}
