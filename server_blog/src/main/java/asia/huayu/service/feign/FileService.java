package asia.huayu.service.feign;

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
    /**
     * 方法createFile作用为：使用feign进行文件上传
     * 使用@RequestHeader方式主动携带请求头      consumes告诉feign这里传递表单数据
     * 如果有多个header可以使用@RequestHeader MultiValueMap<String, String> headers传递
     * 传参数这样MultiValueMap<String, String> headers = new HttpHeaders();
     * headers.put("Authorization", Collections.singletonList("Basic YWRtaW46QFdUTDE5OTIwMTE4MDI3MQ=="));
     * headers.add("Content-Type","text/plain");
     *
     * @param multipartFile
     * @param token
     * @return asia.huayu.common.entity.Result
     * @throws
     * @author RainZiYu
     */

    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result createFile(@RequestPart("file") MultipartFile multipartFile, @RequestHeader("token") String token);

    @PostMapping(value = "/file/path", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result createFile(@RequestPart("file") MultipartFile multipartFile, @RequestHeader("token") String token, @RequestPart String path);

    @GetMapping(value = "/pic")
    Response generatePicByKeyword(@RequestParam String keyword);

    @GetMapping("/file/exist")
    Result<Boolean> checkFileExist(@RequestParam String filePath, @RequestHeader("token") String token);

    @GetMapping("/file")
    Result getFileAccessUrl(@RequestParam String filePath, @RequestHeader("token") String token);
}
