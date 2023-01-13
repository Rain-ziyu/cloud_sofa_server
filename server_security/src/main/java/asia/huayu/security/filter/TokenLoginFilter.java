package asia.huayu.security.filter;


import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.entity.SecurityUser;
import asia.huayu.security.entity.SecurityUserInfo;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.BaseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;
    private final AuthenticationManager authenticationManager;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    // 1 获取表单提交用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 获取 json 格式数据 application/json   提交数据
        try {
            SecurityUserInfo user = new ObjectMapper().readValue(request.getInputStream(), SecurityUserInfo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceProcessException("登录出现异常", e);
        }
    }

    // 2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        // 认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();
        // 根据用户名生成token
        String token = tokenManager.createToken(user.getUsername());
        // 把用户名称和用户权限列表放到redis
        redisTemplate.opsForValue().set(user.getUsername(), user.getPermissionValueList());

        ResponseUtil.out(response, Result.OK(BaseUtil.LoginSuccess, token));
    }

    // 3 认证失败调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        ResponseUtil.out(response, Result.ERROR("认证失败"));
    }
}
