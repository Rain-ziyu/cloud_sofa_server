package asia.huayu.security.security;


import asia.huayu.common.entity.Result;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.util.SystemValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 退出处理器
public class TokenLogoutHandler implements LogoutHandler {
    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenLogoutHandler(TokenManager tokenManager, RedisTemplate redisTemplate) {
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 1 从header里面获取token
        // 2 token不为空，移除token，从redis删除token
        String token = request.getHeader("token");
        if (token != null) {
            // 移除
            tokenManager.removeToken(token);
            // 从token获取用户名
            String username = null;
            try {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            } catch (Exception e) {
                ResponseUtil.out(response, Result.OK("注销的token不存在"));
            }

            redisTemplate.delete(SystemValue.ONLINE_USER_AUTH + username);
            // 从登录用户中移除 在线用户信息
            redisTemplate.opsForHash().delete(SystemValue.LOGIN_USER, username);
            // 删除登陆用户数
            String ipAddress = IpUtil.getIpAddress(request);
            String ipSource = IpUtil.getIpSource(ipAddress);
            String ipProvince = IpUtil.getIpProvince(ipSource);
            redisTemplate.opsForHash().increment(SystemValue.USER_AREA, ipProvince, -1L);
        }
        ResponseUtil.out(response, Result.OK("注销成功"));
    }
}
