package asia.huayu;

import asia.huayu.mapper.ElasticsearchMapper;
import asia.huayu.model.dto.ArticleSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author RainZiYu
 * @Date 2023/3/2
 */
@SpringBootTest
public class AdminSpringBootApplicationTest {
    @Autowired
    private ElasticsearchMapper elasticsearchMapper;

    @Test
    void saveData() {
        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO();
        articleSearchDTO.setId(10);
        articleSearchDTO.setArticleContent("aaa");
        articleSearchDTO.setArticleTitle("wwl");
        elasticsearchMapper.save(articleSearchDTO);
    }
}
