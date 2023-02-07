package asia.huayu.service;

import asia.huayu.common.entity.Result;
import asia.huayu.service.fallback.FileFallbackService;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@FeignClient(value = "cloud-sofa-server-file", fallback = FileFallbackService.class)
public interface FileService {
    @PostMapping("/file")
    Result createFile(@RequestAttribute MultipartFile multipartFile);

    @GetMapping(value = "/pic", consumes = MediaType.APPLICATION_PROBLEM_JSON_VALUE)
    Response generatePicByKeyword(@RequestParam String keyword);
}
