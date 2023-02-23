package asia.huayu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
// 设置哪些包需要JPA映射支持
@EnableElasticsearchRepositories(
        basePackages = "asia.huayu"
)
public class ElasticsearchTemplateProducer {
}
