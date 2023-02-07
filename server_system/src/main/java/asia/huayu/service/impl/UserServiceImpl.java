package asia.huayu.service.impl;

import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.entity.User;
import asia.huayu.mapper.UserMapper;
import asia.huayu.service.FileService;
import asia.huayu.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @author User
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-01-12 14:04:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private FileService fileService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * 方法<code>createUser</code>作用为：
     * 创建用户，自动加密密码
     *
     * @param user
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    @Override
    @Transactional
    public User createUser(User user, String token) {
        // 判断用户名是否重复
        Integer userCountByName = userMapper.selectUserCountByName(user.getUsername());
        if (userCountByName > 0) {
            throw new ServiceProcessException("用户名已存在");
        }
        // 头像生成
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
        // 明文密码进行加密存入数据库
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIsDeleted(0);

        userMapper.createUser(user);
        return user;
    }

    /**
     * 方法<code>selectUser</code>作用为：
     * 根据用户id查询
     *
     * @param userId
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    @Override
    public User selectUser(Integer userId) {
        User user = userMapper.selectById(userId);
        return user;
    }

    /**
     * 方法<code>selectUser</code>作用为：
     * 根据用户名查询
     *
     * @param userName
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    @Override
    public User selectUser(String userName) {
        User user = userMapper.selectUserByName(userName);
        return user;
    }

}




