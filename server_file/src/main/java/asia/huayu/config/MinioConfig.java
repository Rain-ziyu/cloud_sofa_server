package asia.huayu.config;

import io.minio.MinioClient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * minio配置类
 *
 * @author YonC
 * @date 2021/9/20 17:36
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
@EnableConfigurationProperties(MinioConfig.class)
public class MinioConfig {
    @Schema(description = "域名地址")
    private String serverUrl;
    @Schema(description = "端口号")
    private int port;
    @Schema(description = "用户名")
    private String accessKey;
    @Schema(description = "密码")
    private String secretKey;
    @Schema(description = "默认true，只允许https")
    private Boolean secure;
    @Schema(description = "存储桶")
    private String bucketName;
    @Schema(description = "配置目录")
    private String configDir;
    @Schema(description = "域名【拼接】")
    private String domainName;

    /**
     * 注入minio
     *
     * @return 返回MinioClient对象
     */
    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
//                自己配置的不应该是 加端口和 ip
                .endpoint(serverUrl, port, secure)
                //.endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
