package asia.huayu.security.filter;


import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import asia.huayu.common.util.IpUtil;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.entity.OnlineUser;
import asia.huayu.security.entity.SecurityUser;
import asia.huayu.security.entity.SecurityUserInfo;
import asia.huayu.security.entity.TokenDTO;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.service.UserLoginInfoService;
import asia.huayu.security.util.SystemEnums;
import asia.huayu.security.util.SystemValue;
import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;
    private final AuthenticationManager authenticationManager;
    private final UserLoginInfoService userLoginInfoService;

    public TokenLoginFilter(TokenManager tokenManager, RedisTemplate redisTemplate, AuthenticationManager authenticationManager, UserLoginInfoService userLoginInfoService) {
        this.authenticationManager = authenticationManager;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.userLoginInfoService = userLoginInfoService;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
    }

    // 1 获取post请求提交的用户名和密码
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        // 获取 json 格式数据 application/json   提交数据
        try {
            SecurityUserInfo user = new ObjectMapper().readValue(request.getInputStream(), SecurityUserInfo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),
                    new ArrayList<>()));
        } catch (IOException e) {
            throw new ServiceProcessException(SystemEnums.LOGIN_ERROR.VALUE, e);
        }
    }

    // 2 认证成功调用的方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult) {
        TokenDTO tokenDTO = new TokenDTO();
        // 认证成功，得到认证成功之后用户信息
        SecurityUser user = (SecurityUser) authResult.getPrincipal();

        tokenDTO.setExpires(new Date(System.currentTimeMillis() + SystemValue.TOKEN_EXPIRATION_TIME));
        // 把用户名称和用户权限列表放到redis   加上失效时间，是为了减小redis缓存数据量，不能根据该值是否存在判断用户token是否失效
        redisTemplate.opsForValue().set(SystemValue.ONLINE_USER_AUTH + user.getUsername(), user.getPermissionValueList(), SystemValue.TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        // 将登陆用户写到redis的set中
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setUserId(Integer.parseInt(user.getSecurityUserInfo().getId()));
        // 存储登陆时间为当前时间+时间偏移 方便获取的时候比较
        onlineUser.setExpireTime(DateUtil.offsetMillisecond(new Date(), Math.toIntExact(SystemValue.TOKEN_EXPIRATION_TIME)));
        onlineUser.setLoginType(1);
        String ipAddress = IpUtil.getIpAddress(request);
        onlineUser.setIpAddress(ipAddress);
        String ipSource = IpUtil.getIpSource(ipAddress);
        onlineUser.setIpSource(ipSource);
        onlineUser.setBrowser(IpUtil.getUserAgent(request).getBrowser().getName());
        onlineUser.setOs(IpUtil.getUserAgent(request).getOperatingSystem().getName());
        onlineUser.setName(user.getUsername());
        userLoginInfoService.addLoginInfo(onlineUser);
        // 根据用户名生成token
        String token = tokenManager.createToken(user.getUsername(), onlineUser);
        // 根据用户名生成refreshToken
        String refreshToken = tokenManager.createRefreshToken(user.getUsername());
        tokenDTO.setToken(token);
        tokenDTO.setRefreshToken(refreshToken);
        // 同步在redis中增加记录登录的地域信息   存在自增加一 不存在更新
        String ipProvince = IpUtil.getIpProvince(ipSource);
        if (redisTemplate.opsForHash().hasKey(SystemValue.USER_AREA, ipProvince)) {
            redisTemplate.opsForHash().increment(SystemValue.USER_AREA, ipProvince, 1L);
        } else {
            redisTemplate.opsForHash().put(SystemValue.USER_AREA, ipProvince, 1L);
        }
        ResponseUtil.out(response, Result.OK(SystemEnums.LOGIN_SUCCESS.VALUE, tokenDTO));
    }

    // 3 认证失败调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        ResponseUtil.out(response, Result.ERROR(SystemEnums.PASSWORD_ERROR.VALUE));
    }
}
