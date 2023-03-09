package asia.huayu.strategy.impl.upload;

import asia.huayu.common.entity.Result;
import asia.huayu.common.util.RequestUtil;
import asia.huayu.service.feign.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author RainZiYu
 * @Date 2023/2/16
 */
@Service
public class MinioUploadStrategyImpl extends AbstractUploadStrategyImpl {
    @Autowired
    FileService fileService;

    @Override
    public Boolean exists(String filePath) {
        String token = RequestUtil.getRequest().getHeader("token");
        // TODO: 判断文件是否已存在
        Result<Boolean> result = fileService.checkFileExist(filePath, token);
        return result.getData() == null ? false : result.getData();
    }

    @Override
    public void upload(String path, String fileName, InputStream inputStream) throws IOException {
        // 根据文件名获取contenttype
        Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileName);
        String contentType = mediaType.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
        MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, contentType, inputStream);
        String token = RequestUtil.getRequest().getHeader("token");
        fileService.createFile(multipartFile, token, path);
    }

    @Override
    public String getFileAccessUrl(String filePath) {
        String token = RequestUtil.getRequest().getHeader("token");
        Result<String> result = fileService.getFileAccessUrl(filePath, token);
        return result.getData();
    }
}
