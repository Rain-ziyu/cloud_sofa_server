package asia.huayu;

import asia.huayu.security.entity.SecurityUser;
import asia.huayu.util.SystemValue;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author RainZiYu
 * @Date 2023/1/12
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScans({@MapperScan("asia.huayu.mapper"), @MapperScan("asia.huayu.auth.mapper")})
public class ServerStartApplication extends SpringBootServletInitializer implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ServerStartApplication.class, args);
    }

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserDetailsService userDetailsService;

    /**
     * 方法configure
     * 作用为：向Spring注册实现了CommandLineRunner的Run方法的子类的bean
     *
     * @param application
     * @return org.springframework.boot.builder.SpringApplicationBuilder
     * @throws
     * @author User
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        return application.sources(ServerStartApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {

        SecurityUser userDetails = (SecurityUser) userDetailsService.loadUserByUsername(SystemValue.SYSTEM_USER);
        // 把用户名称和用户权限列表放到redis
        redisTemplate.opsForValue().set(userDetails.getUsername(), userDetails.getPermissionValueList());
    }
}