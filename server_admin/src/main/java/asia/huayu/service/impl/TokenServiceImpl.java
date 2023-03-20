package asia.huayu.service.impl;

import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.security.TokenManager;
import asia.huayu.service.TokenService;
import asia.huayu.util.SystemValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RainZiYu
 * @Date 2023/2/7
 */
@Service
public class TokenServiceImpl implements TokenService {
    private final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
    @Autowired
    TokenManager tokenManager;

    @Override
    public String getSystemToken() {
        // todo:系统用户  待传递真实OnlineUser
        String token = concurrentHashMap.getOrDefault("token", tokenManager.createToken(SystemValue.SYSTEM_USER, new OnlineUser()));
        try {
            // 如果没有问题
            String userInfoFromToken = tokenManager.getUserInfoFromToken(token);
            concurrentHashMap.put("token", token);
            return token;
        } catch (Exception e) {
            // 如果token解析失败
            token = tokenManager.createToken(SystemValue.SYSTEM_USER, new OnlineUser());
            concurrentHashMap.put("token", token);
            return token;
        }
    }
}
