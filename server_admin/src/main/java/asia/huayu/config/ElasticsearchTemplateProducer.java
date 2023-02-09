package asia.huayu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
// 设置那些包需要JPA映射支持
@EnableElasticsearchRepositories(
        basePackages = "asia.huayu.es"
)
public class ElasticsearchTemplateProducer {
    @Autowired
    ElasticsearchOperations operations;

    @Bean
    public ElasticsearchOperations createElasticsearchTemplate() {
        // ...
        return operations;
    }
}
