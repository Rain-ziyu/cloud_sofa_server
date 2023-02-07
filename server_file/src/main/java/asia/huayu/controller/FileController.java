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
    public Result uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        return restProcessor(() -> {
            Boolean upload = false;
            String filename = MinioService.getFilename(multipartFile);
            try {

                upload = minioService.upload(multipartFile, filename);
                String previewFileUrl = minioService.getPreviewFileUrl(filename);
                return Result.OK("图片上传完成", previewFileUrl);
            } catch (IOException e) {
                throw new ServiceProcessException("文件上传失败", e);
            } catch (Exception e) {
                throw new ServiceProcessException("文件上传之后获取预览链接失败", e);
            }
        });
    }

}
