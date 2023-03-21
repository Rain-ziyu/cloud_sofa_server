package asia.huayu.strategy.context;

import asia.huayu.enums.MarkdownTypeEnum;
import asia.huayu.strategy.ArticleImportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author User
 * MD格式文章导入
 */
@Service
public class ArticleImportStrategyContext {

    @Autowired
    private Map<String, ArticleImportStrategy> articleImportStrategyMap;

    public String importArticles(MultipartFile file, String type) {
        String articleId = articleImportStrategyMap.get(MarkdownTypeEnum.getMarkdownType(type)).importArticles(file);
        return articleId;
    }
}
