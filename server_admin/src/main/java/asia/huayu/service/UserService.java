package asia.huayu.service;

import asia.huayu.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author User
 * @description 针对表【user(用户表)】的数据库操作Service
 * @createDate 2023-01-12 14:04:05
 */
public interface UserService extends IService<User> {
    /**
     * 方法<code>createUser</code>作用为：
     * 创建用户，自动加密密码
     *
     * @param user
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    User createUser(User user, String token);

    /**
     * 方法<code>selectUser</code>作用为：
     * 根据用户id查询
     *
     * @param userId
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    User selectUser(Integer userId);

    /**
     * 方法<code>selectUser</code>作用为：
     * 根据用户名查询
     *
     * @param userName
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    User selectUser(String userName);

    /**
     * 方法updateUserInfoByUserName作用为：
     * 修改用户信息通过用户名
     *
     * @param user
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    User updateUserInfoByUserName(User user);

    Integer deleteUserByUserName(String userName);

    IPage<User> selectUserListByPage(Page<User> userPage);

    IPage<User> selectUserFuzzy(String nickName, int page, int pageSize);
}
