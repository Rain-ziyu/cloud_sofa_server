package asia.huayu.service;

import asia.huayu.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@FeignClient(value = "cloud-sofa-server-file")
public interface FileService {
    @PostMapping("/file")
    Result createFile(@RequestAttribute MultipartFile multipartFile);
}
