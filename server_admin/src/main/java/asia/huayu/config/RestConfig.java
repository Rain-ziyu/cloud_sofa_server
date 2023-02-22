package asia.huayu.config;

import lombok.Setter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author RainZiYu
 * @Date 2022/11/28
 */
@Configuration
public class RestConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestService getRestTemplateForHttp() {
        RestTemplate restTemplate = new RestTemplate();
        RestService restService = new RestService();
        restService.setRestTemplate(restTemplate);
        return restService;
    }

    @Setter
    public static class RestService {
        public RestTemplate restTemplate;
    }
}
