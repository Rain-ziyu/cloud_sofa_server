package asia.huayu.config;

import asia.huayu.enums.UploadModeEnum;
import asia.huayu.strategy.UploadStrategy;
import asia.huayu.strategy.impl.upload.MinioUploadStrategyImpl;
import asia.huayu.strategy.impl.upload.OssUploadStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author RainZiYu
 * @Date 2023/2/17
 */
@Configuration
public class UploadConfig {
    @Autowired
    MinioUploadStrategyImpl minioUploadStrategy;
    @Autowired
    OssUploadStrategyImpl ossUploadStrategy;

    @Bean("UploadStrategyMap")
    public Map<String, UploadStrategy> setUploadMap() {
        HashMap hashMap = new HashMap();
        hashMap.put(UploadModeEnum.OSS.getStrategy(), ossUploadStrategy);
        hashMap.put(UploadModeEnum.MINIO.getStrategy(), minioUploadStrategy);
        return hashMap;
    }
}
