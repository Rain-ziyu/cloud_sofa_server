package asia.huayu.security.security;


import asia.huayu.common.entity.Result;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.util.SystemValue;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

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

            // 从token获取用户名
            String username = null;
            try {
                username = SecurityContextHolder.getContext().getAuthentication().getName();
            } catch (Exception e) {
                ResponseUtil.out(response, Result.OK("注销的token不存在"));
                return;
            }
            // 移除
            tokenManager.removeToken(token);
            // 不再直接移除用户信息 而是移除用户token 用户Auth信息等待自动过期
            // redisTemplate.delete(SystemValue.ONLINE_USER_AUTH + username);
            // 当用户所有的token都不存在时(用户完全退出),从登录用户中移除 在线用户信息
            //     redisTemplate.opsForHash().delete(SystemValue.LOGIN_USER, token);
            // 删除登陆用户区域信息   完全交由定时任务来执行 不进行实时更新  更新逻辑进行实时更新
            String ipAddress = IpUtil.getIpAddress(request);
            String ipSource = IpUtil.getIpSource(ipAddress);
            String ipProvince = IpUtil.getIpProvince(ipSource);
            redisTemplate.opsForHash().increment(SystemValue.USER_AREA, ipProvince, -1L);
        }
        ResponseUtil.out(response, Result.OK("注销成功"));
    }

    /**
     * 方法userExitsCompletely作用为：
     * 判断用户是否完全退出 true用户完全退出  false 未完全退出
     *
     * @param username
     * @return java.lang.Boolean
     * @throws
     * @author RainZiYu
     */
    private Boolean userExitsCompletely(String username) {
        Set<String> members = redisTemplate.opsForSet().members(SystemValue.TOKEN_PREFIX + username);
        if (ObjectUtil.isNotEmpty(members)) {
            for (String member : members) {
                if (redisTemplate.hasKey(SystemValue.TOKEN_PREFIX + member)) {
                    return false;
                }
            }
        }
        return true;
    }
}
