package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.entity.User;
import asia.huayu.service.FileService;
import asia.huayu.service.UserService;
import cn.hutool.core.util.StrUtil;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * @author RainZiYu
 * @Date 2023/1/13
 */
@RestController
@CrossOrigin
@Slf4j
public class LoginController extends BaseController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Resource
    private FileService fileService;

    @PostMapping("/user")
    public Result createUser(@RequestBody @Valid User user, @RequestHeader("token") String token) {
        return restProcessor(() -> {
            if (user.getSalt() == null || user.getSalt().isBlank()) {
                MultipartFile multipartFile;
                try (Response response = fileService.generatePicByKeyword(user.getUsername().substring(0, 1))) {
                    // 获取response中的所有header
                    Map<String, Collection<String>> headers = response.headers();
                    String fileName = null;
                    Set<String> strings = headers.keySet();
                    Iterator<String> iterator = strings.iterator();
                    // 从中找出content-disposition来获取文件名
                    while (iterator.hasNext()) {
                        String next = iterator.next();
                        if ("content-disposition".equals(next)) {
                            Collection<String> strings1 = headers.get(next);
                            Iterator<String> iterator1 = strings1.iterator();
                            String next1 = iterator1.next();
                            fileName = next1.split("filename=")[1];
                            if (StrUtil.isBlank(fileName)) {
                                throw new ServiceProcessException("生成默认头像失败");
                            }
                            break;
                        }
                    }
                    // 根据文件名获取contenttype
                    Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(fileName);
                    String contentType = mediaType.orElse(MediaType.APPLICATION_OCTET_STREAM).toString();
                    multipartFile = new MockMultipartFile(fileName, fileName, contentType, response.body().asInputStream());
                } catch (IOException e) {
                    throw new ServiceProcessException("获取生成图片异常", e);
                }
                Result<Object> result = fileService.createFile(multipartFile, token);
                if (result.isSuccess()) {
                    user.setSalt(result.getData().toString());
                } else {
                    throw new ServiceProcessException("获取默认头像地址失败");
                }
            }
            User createUser = userService.createUser(user);
            return Result.OK(createUser);
        });
    }
}
