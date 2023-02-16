package asia.huayu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.huayu.mapper")
public class AuroraSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraSpringbootApplication.class, args);
    }

}
