package asia.huayu.service.impl;

import asia.huayu.entity.User;
import asia.huayu.mapper.UserMapper;
import asia.huayu.service.FileService;
import asia.huayu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author User
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-01-12 14:04:05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileService fileService;

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
    public User createUser(User user) {
        // 明文密码进行加密存入数据库
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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




