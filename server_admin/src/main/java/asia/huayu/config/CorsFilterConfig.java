package asia.huayu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author RainZiYu
 * @Date 2023/2/9
 */
@Configuration
public class CorsFilterConfig {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();

        /*设置你要允许的网站域名  当想要设置全部请求不拦截不能直接设置* 而是在addAllowedOriginPattern中设置
        config.addAllowedOrigin("http://localhost:8080");
        允许所有域名进行跨域调用 */
        config.addAllowedOriginPattern("*");
        // 允许跨域发送cookie
        config.setAllowCredentials(true);
        // 放行全部原始头信息
        config.addAllowedHeader("*");
        // 允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
