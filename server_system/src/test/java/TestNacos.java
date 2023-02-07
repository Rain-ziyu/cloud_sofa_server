
import asia.huayu.ServerStartApplication;
import asia.huayu.security.security.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author RainZiYu
 * @Date 2023/1/11
 */
@Slf4j
@RefreshScope
@SpringBootTest(classes = ServerStartApplication.class)
public class TestNacos {
    @Autowired
    TokenManager tokenManager;

    @Test
    public void getParams() {
        log.info("读取到的端口号是{}", port);
    }
    @Value("${server.port}")
    private int port;

    @Test
    public void checkToken() {
        String aa = tokenManager.getUserInfoFromToken("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSSkzJzcxT0lFKrShQsjI0Mzc1NzUzMTCpBQB3PHCvIAAAAA.znD9wN_02QknS1jfwmOB0TPxGCxrQP6UIdywoF9HyV8Kc8j3c37lCqYRQ8txkHqNWVwcrSi4B0ak1tqTX_615w");
        log.info(aa);
    }
}
