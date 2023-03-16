package asia.huayu;

import asia.huayu.mapper.ElasticsearchMapper;
import asia.huayu.model.dto.ArticleSearchDTO;
import asia.huayu.quartz.AuroraQuartz;
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
    @Autowired
    private AuroraQuartz auroraQuartz;

    @Test
    void saveData() {
        ArticleSearchDTO articleSearchDTO = new ArticleSearchDTO();
        articleSearchDTO.setId(10);
        articleSearchDTO.setArticleContent("aaa");
        articleSearchDTO.setArticleTitle("wwl");
        elasticsearchMapper.save(articleSearchDTO);
    }

    @Test
    void testUserArea() {
        auroraQuartz.statisticalUserArea();
    }


    /**
     * 方法testImportSwagger作用为：
     * 初始化resource表以及接口的权限信息
     *
     * @param
     * @return void
     * @throws
     * @author RainZiYu
     */
    @Test
    void testImportSwagger() {
        auroraQuartz.importSwagger("http://localhost:8080/user/v3/api-docs/blog", "/user");
        auroraQuartz.importSwagger("http://localhost:3399/admin/v3/api-docs/admin", "/admin");
    }
}
