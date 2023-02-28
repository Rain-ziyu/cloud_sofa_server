package asia.huayu.service.impl;

import asia.huayu.entity.UserLoginInfo;
import asia.huayu.mapper.UserLoginInfoMapper;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.service.UserLoginInfoService;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【user_login_info(用户登录信息)】的数据库操作Service实现
 * @createDate 2023-02-16 15:47:44
 */
@Service
public class UserLoginInfoServiceImpl extends ServiceImpl<UserLoginInfoMapper, UserLoginInfo>
        implements IService<UserLoginInfo>, UserLoginInfoService {

    @Autowired
    private UserLoginInfoMapper userLoginInfoMapper;

    @Override
    public void addLoginInfo(OnlineUser onlineUser) {
        //  记录用户登录信息到数据库
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserId(onlineUser.getUserId());
        userLoginInfo.setLoginTime(onlineUser.getExpireTime());
        userLoginInfo.setLoginType(onlineUser.getLoginType());
        userLoginInfo.setIpAddress(onlineUser.getIpAddress());
        userLoginInfo.setIpSource(onlineUser.getIpSource());
        userLoginInfo.setBrowser(onlineUser.getBrowser());
        userLoginInfo.setOs(onlineUser.getOs());
        userLoginInfoMapper.insert(userLoginInfo);
    }
}




