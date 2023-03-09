package asia.huayu.service.impl;

import asia.huayu.constant.CommonConstant;
import asia.huayu.security.security.TokenManager;
import asia.huayu.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author RainZiYu
 * @Date 2023/3/9
 * 用于生成内部账户
 */
@Service
public class TokenServiceImpl implements TokenService {
    private final ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap();
    @Autowired
    TokenManager tokenManager;

    @Override
    public String getSystemToken() {
        String token = concurrentHashMap.getOrDefault("token", tokenManager.createToken(CommonConstant.SYSTEM_USER));
        try {
            // 如果没有问题
            String userInfoFromToken = tokenManager.getUserInfoFromToken(token);
            concurrentHashMap.put("token", token);
            return token;
        } catch (Exception e) {
            // 如果token解析失败
            token = tokenManager.createToken(CommonConstant.SYSTEM_USER);
            concurrentHashMap.put("token", token);
            return token;
        }
    }
}