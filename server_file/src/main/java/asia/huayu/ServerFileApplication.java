package asia.huayu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author RainZiYu
 * @Date ${DATE}
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ServerFileApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerFileApplication.class, args);
    }
}