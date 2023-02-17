package asia.huayu.mapper;

import asia.huayu.entity.UserLoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
/**
 * @author User
 * @description 针对表【user_login_info(用户登录信息)】的数据库操作Mapper
 * @createDate 2023-02-16 15:47:44
 * @Entity asia.huayu.entity.UserLoginInfo
 */
public interface UserLoginInfoMapper extends BaseMapper<UserLoginInfo> {

}




