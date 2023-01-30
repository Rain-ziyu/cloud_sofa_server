package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Service
public class FileFallbackService implements FileService {
    @Override
    public Result createFile(MultipartFile multipartFile) {
        return Result.ERROR(405, "服务降级返回,---FileFallbackService");
    }
}
