package asia.huayu.security.config;

import asia.huayu.security.filter.TokenAuthFilter;
import asia.huayu.security.filter.TokenLoginFilter;
import asia.huayu.security.security.*;
import asia.huayu.security.service.UserLoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

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
    private final AccessDecisionManager accessDecisionManager;
    private final FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    // 如果没有配置则默认为空字符串
    @Value("${security.permitAllUri:/wwl}")
    private String permitAllUri;

    @Autowired(required = false)
    public TokenWebSecurityConfig(UserDetailsService userDetailsService, DefaultPasswordEncoder defaultPasswordEncoder,
                                  TokenManager tokenManager, RedisTemplate redisTemplate, UserLoginInfoService userLoginInfoService,
                                  AccessDecisionManager accessDecisionManager, FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource, AccessDeniedHandlerImpl accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.defaultPasswordEncoder = defaultPasswordEncoder;
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;
        this.userLoginInfoService = userLoginInfoService;
        this.accessDecisionManager = accessDecisionManager;
        this.filterInvocationSecurityMetadataSource = filterInvocationSecurityMetadataSource;
        this.accessDeniedHandler = accessDeniedHandler;
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
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    // 设置自定义权限判断
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(accessDecisionManager);
                        return o;
                    }
                });
        http.exceptionHandling()
                // 设置权限不足时处理
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(new UnauthEntryPoint())// 登陆失败
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

