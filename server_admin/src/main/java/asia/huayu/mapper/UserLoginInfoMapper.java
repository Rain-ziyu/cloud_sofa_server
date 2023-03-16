package asia.huayu.mapper;

import asia.huayu.entity.UserLoginInfo;
import asia.huayu.security.entity.OnlineUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
/**
 * @author User
 * @description 针对表【user_login_info(用户登录信息)】的数据库操作Mapper
 * @createDate 2023-02-16 15:47:44
 * @Entity asia.huayu.entity.UserLoginInfo
 */
public interface UserLoginInfoMapper extends BaseMapper<UserLoginInfo> {
    /**
     * 方法getOnlineUserLoginInfo作用为：
     * 获取在线用户的登录信息   暂不使用 因为redis中已包含所有待查询数据
     *
     * @param
     * @return asia.huayu.entity.UserLoginInfo
     * @throws
     * @author RainZiYu
     */
    List<UserLoginInfo> getOnlineUserLoginInfo(List<OnlineUser> onlineUsers);
}




