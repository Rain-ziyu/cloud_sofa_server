package asia.huayu.service.impl;

import asia.huayu.auth.service.RoleService;
import asia.huayu.constant.CommonConstant;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemValue;
import asia.huayu.service.RedisService;
import asia.huayu.service.TokenService;
import asia.huayu.util.UserUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
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
    @Autowired
    private RedisService redisService;
    @Autowired
    private RoleService roleService;

    @Override
    public String getSystemToken() {
        String token = concurrentHashMap.get("token");
        try {
            // 如果没有问题
            String userInfoFromToken = tokenManager.getUserInfoFromToken(token);
            concurrentHashMap.put("token", token);
            return token;
        } catch (Exception e) {
            // TODO:带传递真实onlineUser
            OnlineUser onlineUser = new OnlineUser();

            // 如果token解析失败  创建系统内置token
            token = tokenManager.createToken(CommonConstant.SYSTEM_USER, onlineUser);
            List<String> selectRoleByUserId = roleService.selectRoleByUserId(CommonConstant.SYSTEM_USER_ID);
            redisService.set(SystemValue.ONLINE_USER_AUTH + CommonConstant.SYSTEM_USER, selectRoleByUserId);
            concurrentHashMap.put("token", token);
            return token;
        }
    }

    @Override
    public String getUserTokenOrSystemToken() {
        Authentication authentication = UserUtil.getAuthentication();
        String token;
        // 如果该用户未认证
        if (ObjectUtil.isNull(authentication) || authentication.getPrincipal().equals("anonymousUser")) {
            //     创建匿名用户进行获取编辑信息
            token = getSystemToken();
        } else {
            token = authentication.getCredentials().toString();
        }
        return token;
    }
}