package asia.huayu.strategy;

import org.springframework.web.multipart.MultipartFile;

public interface ArticleImportStrategy {

    String importArticles(MultipartFile file);

}
