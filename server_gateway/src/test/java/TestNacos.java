import asia.huayu.gateway.ServerStartApplication;
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
    @Value("${server.port}")
    private int port;

    @Test
    public void getParams() {
        log.info("读取到的端口号是{}", port);
    }

    @Test
    public void checkToken() {
        String aaa = tokenManager.createToken("aaa");
        String aaa1 = tokenManager.createToken("aaa");
        String userInfoFromToken = tokenManager.getUserInfoFromToken(aaa);
        String userInfoFromToken1 = tokenManager.getUserInfoFromToken(aaa1);
        log.info(userInfoFromToken);
        log.info(userInfoFromToken1);
        String aa = tokenManager.getUserInfoFromToken("eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAA_6tWKi5NUrJSSkzJzcxT0lFKrShQsjI0Mzc1NzUzMTCpBQB3PHCvIAAAAA.znD9wN_02QknS1jfwmOB0TPxGCxrQP6UIdywoF9HyV8Kc8j3c37lCqYRQ8txkHqNWVwcrSi4B0ak1tqTX_615w");
        log.info(aa);
    }

}
