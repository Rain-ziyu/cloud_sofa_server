package asia.huayu.config;

/**
 * @author RainZiYu
 * @Date 2022/11/10
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@EnableOpenApi
@Configuration
public class SwaggerConfig {
//
//    @Value("${spring.profiles.active:NA}")
//    private String active;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)  // OAS_30
                .enable(true)
                .apiInfo(apiInfo())
                // Base URL 用于控制生成的 curl命令的地址
                .host("localhost:443")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("Card Server")
                .contact(new Contact("RainZiYu", null, null))
                .version("1.0")
                .build();
    }


    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeyList = new ArrayList();
        // name配置项标识该apikey的name  需要一致才会携带该请求头    keyname貌似没有用
        apiKeyList.add(new ApiKey("token", "Auth", "header"));
        return apiKeyList;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build());
        return securityContexts;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        // 这里的token代表引用哪个apikey
        securityReferences.add(new SecurityReference("token", authorizationScopes));
        return securityReferences;
    }
}
