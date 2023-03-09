package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.service.feign.FileService;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class FileFallbackService implements FileService {
    @Override
    public Result createFile(MultipartFile multipartFile, String token) {
        return Result.ERROR(405, "服务降级返回,---FileFallbackService");
    }

    @Override
    public Result createFile(MultipartFile multipartFile, String token, String path) {
        return Result.ERROR(405, "服务降级返回,---FileFallbackService");
    }

    @Override
    public Response generatePicByKeyword(String keyword) {
        throw new ServiceProcessException("生成头像图片Feign调用异常");
    }

    @Override
    public Result checkFileExist(String filePath, String token) {
        return null;
    }

    @Override
    public Result getFileAccessUrl(String filePath, String token) {
        return Result.ERROR(405, "服务降级返回,---FileFallbackService");
    }
}
