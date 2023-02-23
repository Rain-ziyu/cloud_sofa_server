// package asia.huayu.config;
//
//
// import lombok.Data;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
//
//
// @Configuration
// @Data
// public class ElasticsearchConfig extends ElasticsearchConfiguration {
//     @Value("${spring.elasticsearch.uris}")
//     String elasticUri;
//     @Value("${spring.elasticsearch.username}")
//     String userName;
//     @Value("${spring.elasticsearch.password}")
//     String passWord;
//
//     // 使用新版的Elasticsearch Java API Client [8.4] 不能使用映射进行控制，因此还是修改为RestHighLevelClient
//     // 因为老版本的操作8.xes存在转换问题，通过尝试与阅读官方文档使用一下方式 注入最新的Elasticsearch Java API Client
//
//
//     @Override
//     public ClientConfiguration clientConfiguration() {
//         return ClientConfiguration.builder() //
//                 .connectedTo(elasticUri) //
//                 .withBasicAuth(userName, passWord)
//                 .build();
//     }
//
//
// }