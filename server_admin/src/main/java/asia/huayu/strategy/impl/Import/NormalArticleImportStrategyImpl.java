package asia.huayu.strategy.impl.Import;

import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.entity.Article;
import asia.huayu.entity.User;
import asia.huayu.model.vo.ArticleVO;
import asia.huayu.service.ArticleService;
import asia.huayu.service.UserService;
import asia.huayu.strategy.ArticleImportStrategy;
import asia.huayu.util.SystemValue;
import asia.huayu.util.UserUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

import static asia.huayu.enums.ArticleStatusEnum.DRAFT;

@Slf4j
@Service("normalArticleImportStrategyImpl")
public class NormalArticleImportStrategyImpl implements ArticleImportStrategy {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;
    @Override
    public String importArticles(MultipartFile file) {
        String articleTitle = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0];
        StringBuilder articleContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while (reader.ready()) {
                articleContent.append((char) reader.read());
            }
        } catch (IOException e) {
            log.error(StrUtil.format("导入文章失败, 堆栈:{}", ExceptionUtil.stacktraceToString(e)));
            throw new ServiceProcessException("导入文章失败");
        }

        String articleId;
        User user = userService.getUserByUsername(UserUtil.getAuthentication().getName());
        List<Article> list = articleService.list(new LambdaQueryWrapper<Article>().eq(Article::getArticleTitle, articleTitle).eq(Article::getUserId, user.getId()));
        if (list.size() == 1) {
            // 如果只有一个该名称的则默认为更新  其余情况都是新建
            Article article = list.get(0);
            // 更新内容
            article.setArticleContent(articleContent.toString());
            articleService.saveOrUpdate(article);
            articleId = article.getId().toString();
        } else {
            // 新建草稿文章
            ArticleVO articleVO = ArticleVO.builder()
                    .articleCover(SystemValue.DEFAULT_ARTICLE_COVER)
                    .articleTitle(articleTitle)
                    .articleContent(articleContent.toString())
                    .status(DRAFT.getStatus())
                    .build();

            articleId = articleService.saveOrUpdateArticle(articleVO);
        }

        return articleId;
    }
}
