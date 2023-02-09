package asia.huayu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author RainZiYu
 * @Date ${DATE}
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ServerAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerAdminApplication.class, args);
    }
}