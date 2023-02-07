package asia.huayu.service;

import asia.huayu.common.entity.Result;
import asia.huayu.service.fallback.FileFallbackService;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@FeignClient(value = "cloud-sofa-server-file", fallback = FileFallbackService.class)
public interface FileService {
    // 使用@RequestHeader方式主动携带请求头      consumes告诉feign这里传递表单数据
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result createFile(@RequestPart("file") MultipartFile multipartFile, @RequestHeader("token") String token);

    @GetMapping(value = "/pic")
    Response generatePicByKeyword(@RequestParam String keyword);
}
