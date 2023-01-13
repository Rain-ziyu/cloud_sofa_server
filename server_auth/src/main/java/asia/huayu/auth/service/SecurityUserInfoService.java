package asia.huayu.auth.service;

import asia.huayu.security.entity.SecurityUserInfo;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
public interface SecurityUserInfoService {
    // 从数据库中取出用户信息
    SecurityUserInfo selectByUsername(String username);

    SecurityUserInfo getById(String id);

}
