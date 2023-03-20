package asia.huayu.security.filter;


import asia.huayu.security.security.TokenManager;
import asia.huayu.security.util.SystemEnums;
import asia.huayu.security.util.SystemValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TokenAuthFilter extends BasicAuthenticationFilter {

    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, TokenManager tokenManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 获取当前认证成功用户权限信息
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        // 判断如果有权限信息，放到权限上下文中   暂时不管有没有都放
        // if (authRequest != null) {
        SecurityContextHolder.getContext().setAuthentication(authRequest);
        // }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从header获取token
        String token = request.getHeader(SystemEnums.AUTH_NAME.VALUE);
        // 解决客户端提交了空的token引发的token无法获取用户信息的问题  如果用户没有传递或者传递的是空字符串 那么不需要设置这次请求的SecurityContextHolder.getContext().setAuthentication
        if (token != null && !token.isBlank()) {
            // 从token获取用户名  如果请求时对方携带了token但是经由tokenManager无法获取对应的用户名则抛出AuthenticationServiceException异常 交由UnauthEntryPoint进行处理
            String username = null;
            try {
                username = tokenManager.getUserInfoFromToken(token);

                // 从redis获取对应权限列表
                List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(SystemValue.ONLINE_USER_AUTH + username);

                Collection<GrantedAuthority> authority = new ArrayList<>();
                for (String permissionValue : permissionValueList) {
                    SimpleGrantedAuthority auth = new SimpleGrantedAuthority(permissionValue);
                    authority.add(auth);
                }

                // 这里也可以不仅放username 也可以查询数据库来存放完整的用户信息
                return new UsernamePasswordAuthenticationToken(username, token, authority);
            } catch (Exception e) {
                // 即使用户携带的token有异常我们也不管了直接返回null   security会有自己的机制可能会在缓存中读到正确的用户token 如果我们抛出异常会导致直接返回请求，不会执行后续controller即使是不需要权限的
                // throw new AuthenticationServiceException("token无法解析", e);
                logger.info("一个用户的token无法解析");
                return null;
            }
        }
        return null;
    }

}
