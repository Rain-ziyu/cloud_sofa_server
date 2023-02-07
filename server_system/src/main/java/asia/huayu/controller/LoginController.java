package asia.huayu.controller;

import asia.huayu.common.controller.base.BaseController;
import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.entity.User;
import asia.huayu.service.FileService;
import asia.huayu.service.UserService;
import com.alibaba.fastjson2.JSON;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author RainZiYu
 * @Date 2023/1/13
 */
@RestController
@CrossOrigin
public class LoginController extends BaseController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @Resource
    private FileService fileService;

    @PostMapping("/user")
    public Result createUser(@RequestBody User user) {
        return restProcessor(() -> {
            if (user.getSalt() == null || user.getSalt().isBlank()) {
                Response response = fileService.generatePicByKeyword(user.getUsername().substring(0, 1));
                MultipartFile multipartFile = null;
                try {
                    multipartFile = new MockMultipartFile(JSON.toJSONString(response.headers()), response.body().asInputStream());
                } catch (IOException e) {
                    throw new ServiceProcessException(e);
                }

                Result result = fileService.createFile(multipartFile);

            }
            User createUser = userService.createUser(user);
            return Result.OK(createUser);
        });
    }
}
