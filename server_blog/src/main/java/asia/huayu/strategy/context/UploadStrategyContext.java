package asia.huayu.strategy.context;

import asia.huayu.strategy.UploadStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

import static asia.huayu.enums.UploadModeEnum.getStrategy;

/**
 * @author User
 * 上传模式
 */
@Service
public class UploadStrategyContext {

    @Value("${upload.mode}")
    private String uploadMode;

    @Autowired
    @Qualifier(value = "UploadStrategyMap")
    private Map<String, UploadStrategy> uploadStrategyMap;

    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(file, path);
    }

    public String executeUploadStrategy(String fileName, InputStream inputStream, String path) {
        return uploadStrategyMap.get(getStrategy(uploadMode)).uploadFile(fileName, inputStream, path);
    }

}
