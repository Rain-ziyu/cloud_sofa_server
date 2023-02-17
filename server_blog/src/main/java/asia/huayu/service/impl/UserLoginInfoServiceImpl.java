package asia.huayu.service.impl;

import asia.huayu.entity.UserLoginInfo;
import asia.huayu.mapper.UserLoginInfoMapper;
import asia.huayu.service.UserLoginInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【user_login_info(用户登录信息)】的数据库操作Service实现
 * @createDate 2023-02-16 15:47:44
 */
@Service
public class UserLoginInfoServiceImpl extends ServiceImpl<UserLoginInfoMapper, UserLoginInfo>
        implements UserLoginInfoService {

}




