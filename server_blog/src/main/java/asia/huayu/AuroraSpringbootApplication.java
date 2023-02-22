package asia.huayu;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScans({@MapperScan("asia.huayu.mapper"), @MapperScan("asia.huayu.auth.mapper")})
public class AuroraSpringbootApplication extends SpringBootServletInitializer implements CommandLineRunner {

    public static void main(String[] args) {
        System.out.println("aaaaa");
        SpringApplication.run(AuroraSpringbootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(AuroraSpringbootApplication.class);
    }


    @Override
    public void run(String... args) throws Exception {
        System.out.println("aaaaa");

    }
}
