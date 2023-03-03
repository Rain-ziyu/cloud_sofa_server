package asia.huayu.config;


import lombok.Data;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@Data
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.uris}")
    String elasticUri;
    @Value("${spring.elasticsearch.username}")
    String userName;
    @Value("${spring.elasticsearch.password}")
    String passWord;


    // 因为老版本的操作8.xes存在转换问题，通过尝试与阅读官方文档使用一下方式 注入最新的Elasticsearch Java API Client
    // 如果之后有时间完全修改面向ES8.x的话 注意:需要使用最新的SpringData 5.x以上,因为SpringData5.x内置的是Elasticsearch Java API Client [8.4]
    // 并且目前支持对象映射 但是很多原有的es客户端被移除比如 ElasticsearchRestTemplate 并且开启以下代码注释来使用最新的ES客户端
    // 以及注意继承extends ElasticsearchConfiguration
    // SpringData5.x参考官网 https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.clients.restclient


    // 之后的记录：      使用新版的Elasticsearch Java API Client [8.4] 会导致原有ElasticsearchRestTemplate等es的连接客户端失效
    // 需要大幅修改原有es的操作API，因此还是修改为RestHighLevelClient兼容原有代码
    // @Override
    // public ClientConfiguration clientConfiguration() {
    //
    //     ClientConfiguration build = ClientConfiguration.builder() //
    //             .connectedTo(elasticUri) //
    //             .withBasicAuth(userName, passWord)
    //             .build();
    //     return build;
    // }

    // 为了兼容ES8.x需要自定义注入RestHighLevelClient指定.setApiCompatibilityMode(true)来要求服务端es表现得与7.x一致
    @Bean
    public RestHighLevelClient getRestHighLevelClient() {
        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord)
        );
        // Create the low-level client
        RestClient httpClient = RestClient.builder(
                        new HttpHost("prod.huayu.asia", 9200)
                ).setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credsProv))
                .build();
        // Create the HLRC
        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
                .setApiCompatibilityMode(true)
                .build();
        return hlrc;
    }


}