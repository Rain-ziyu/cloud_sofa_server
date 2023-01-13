
import asia.huayu.ServerStartApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
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
    @Value("${server.por}")
    private int port;

    @Test
    public void getParams() {
        log.info("读取到的端口号是{}", port);
    }
}
