package asia.huayu.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author User
 * 集成swagger与OPENAPI
 */
@Configuration
public class Knife4jConfig {


    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"asia.huayu.controller"};
        return GroupedOpenApi.builder().group("blog模块")
                .pathsToMatch(paths)
                .addOperationCustomizer((operation, handlerMethod) -> {
                    return operation.addParametersItem(new HeaderParameter().name("token").example("token").description("token").schema(new StringSchema()._default("BR").name("token").description("token")));
                })
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("aurora-cloud文档")
                        .version("1.0")
                        .description("server_admin")
                        .termsOfService("https://prod.huayu.asia/api")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }

}
