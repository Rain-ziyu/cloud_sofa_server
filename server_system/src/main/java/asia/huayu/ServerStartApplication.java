package asia.huayu;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author RainZiYu
 * @Date 2023/1/12
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScans({@MapperScan("asia.huayu.mapper"), @MapperScan("asia.huayu.auth.mapper")})
public class ServerStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerStartApplication.class, args);
    }
}