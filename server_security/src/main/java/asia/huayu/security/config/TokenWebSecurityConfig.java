package asia.huayu.security.config;

import asia.huayu.security.filter.TokenAuthFilter;
import asia.huayu.security.filter.TokenLoginFilter;
import asia.huayu.security.security.DefaultPasswordEncoder;
import asia.huayu.security.security.TokenLogoutHandler;
import asia.huayu.security.security.TokenManager;
import asia.huayu.security.security.UnauthEntryPoint;
import asia.huayu.security.service.UserLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author RainZiYu
 * @Date 2022/11/18
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class TokenWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenManager tokenManager;
    private final RedisTemplate redisTemplate;
    private final DefaultPasswordEncoder defaultPasswordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserLoginInfoService userLoginInfoService;
    // 如果没有配置则默认为空字符串
    @Value("${security.permitAllUri:/}")
    private String permitAllUri;

    @Autowired(required = false)
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate, UserLoginInfoService userLoginInfoService) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.userLoginInfoService = userLoginInfoService;
    }

    /**
     * 配置设置
     *
     * @param http
     * @throws Exception
     */
    // 设置退出的地址和token，redis操作地址
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] permitAllUris = permitAllUri.split(",");
        http
                .exceptionHandling()
                .authenticationEntryPoint(new UnauthEntryPoint())// 没有权限访问
                .and().csrf().disable()
                .authorizeRequests()
                // 走 Spring Security 过滤器链的放行 虽然会放行该请求 但是仍然会走security的过滤器链
                .antMatchers(permitAllUris).permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/logout")// 退出路径
                .addLogoutHandler(new TokenLogoutHandler(tokenManager, redisTemplate)).and()
                .addFilter(new TokenLoginFilter(tokenManager, redisTemplate, authenticationManager(), userLoginInfoService))
                .addFilter(new TokenAuthFilter(authenticationManager(), tokenManager, redisTemplate)).httpBasic();
    }

    // 调用userDetailsService和密码处理
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(defaultPasswordEncoder);
    }

    // 不进行认证的路径，可以直接访问  不走 Spring Security 过滤器链
    @Override
    public void configure(WebSecurity web) {
        // 注意swagger占有很多path 需要逐个开放     不能全部不拦截 login接口将会消失 导致没有请求接口
        web.ignoring().antMatchers("/api/**", "/v3/api-docs", "/webjars/**", "/doc.html", "/swagger-ui/**",
                "/swagger-resources/**", "/v2/**", "/v3/**")
        ;

    }
}

