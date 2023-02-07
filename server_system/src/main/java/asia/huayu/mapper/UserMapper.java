package asia.huayu.mapper;

import asia.huayu.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author User
 * @description 针对表【user(用户表)】的数据库操作Mapper
 * @createDate 2023-01-12 14:04:05
 * @Entity asia.huayu.entity.User
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 方法<code>createUser</code>作用为：
     * 底层使用useGeneratedKeys="true" keyProperty="id"来实现id回写
     *
     * @param user
     * @return int
     * @throws
     * @author RainZiYu
     */
    int createUser(User user);

    /**
     * 方法<code>selectUserByName</code>作用为：
     * 根据用户名查询用户
     *
     * @param userName
     * @return asia.huayu.entity.User
     * @throws
     * @author RainZiYu
     */
    User selectUserByName(String userName);

    /**
     * 方法selectUserCountByName作用为：
     * 判断当前用户名是否已经存在
     *
     * @param userName
     * @return java.lang.Integer
     * @throws
     * @author RainZiYu
     */
    Integer selectUserCountByName(String userName);
}




