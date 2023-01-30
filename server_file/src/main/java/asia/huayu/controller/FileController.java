package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@CrossOrigin
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {
    @Autowired
    MinioService minioService;

    @PostMapping
    public Result createFile(@RequestAttribute MultipartFile multipartFile) {
        return restProcessor(() -> {
            Boolean upload = false;
            try {
                upload = minioService.upload(multipartFile, MinioService.getFilename(multipartFile));
            } catch (IOException e) {
                throw new ServiceProcessException("文件上传失败", e);
            }
            return Result.OK(upload);
        });
    }

}
