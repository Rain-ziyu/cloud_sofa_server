package asia.huayu.service;

import asia.huayu.entity.TempArticle;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author User
 * @description 针对表【temp_article(用于存储临时的未登录用户的发布文章)】的数据库操作Service
 * @createDate 2023-03-14 18:07:07
 */
public interface TempArticleService extends IService<TempArticle> {
    /**
     * 方法bindTempArticle作用为：
     * 为临时文章绑定到真正注册登录的用户
     *
     * @param tempArticleId
     * @return void
     * @throws
     * @author RainZiYu
     */
    void bindTempArticle(List<Long> tempArticleId);

    /**
     * 方法getArticleIds作用为：
     * 通过临时文章id获取文章id
     *
     * @param tempArticleId
     * @return java.util.List<java.lang.Long>
     * @throws
     * @author RainZiYu
     */
    List<Integer> getArticleIds(List<Long> tempArticleId);

    TempArticle getTempArticleByArticleId(String articleId);
}
