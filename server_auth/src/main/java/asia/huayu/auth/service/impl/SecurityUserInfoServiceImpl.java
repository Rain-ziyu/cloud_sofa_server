package asia.huayu.auth.service.impl;

import asia.huayu.auth.mapper.SecurityUserInfoMapper;
import asia.huayu.auth.service.SecurityUserInfoService;
import asia.huayu.security.entity.SecurityUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
@Service
public class SecurityUserInfoServiceImpl implements SecurityUserInfoService {
    @Autowired
    private SecurityUserInfoMapper securityUserInfoMapper;

    @Override
    public SecurityUserInfo selectByUsername(String username) {
        return securityUserInfoMapper.getSecurityUserInfoByName(username);
    }


    @Override
    public SecurityUserInfo getById(String id) {
        SecurityUserInfo securityUserInfoById = securityUserInfoMapper.getSecurityUserInfoById(id);
        return securityUserInfoById;
    }
}
