package asia.huayu.service.impl;

import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.service.UserLoginInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @description 针对表【user_login_info(用户登录信息)】的数据库操作Service实现
 * @createDate 2023-02-16 15:47:44
 */
@Slf4j
@Service
public class UserLoginInfoServiceImpl
        implements UserLoginInfoService {

    @Override
    public void addLoginInfo(OnlineUser onlineUser) {
        log.info("通过调用file模块的登录，不记录进数据库");
    }
}




