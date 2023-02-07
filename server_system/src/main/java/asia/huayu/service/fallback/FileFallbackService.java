package asia.huayu.service.fallback;

import asia.huayu.common.entity.Result;
import asia.huayu.service.FileService;
import com.alibaba.fastjson2.JSON;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Component
public class FileFallbackService implements FileService {
    @Override
    public Result createFile(MultipartFile multipartFile) {
        return Result.ERROR(405, "服务降级返回,---FileFallbackService");
    }

    @Override
    public Response generatePicByKeyword(String keyword) {
        Response body = Response.builder().body(JSON.toJSONString(
                        Result.ERROR(405, "服务降级返回,---FileFallbackService")),
                StandardCharsets.UTF_8).build();
        return body;
    }
}
