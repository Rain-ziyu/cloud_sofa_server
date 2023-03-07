package asia.huayu;

import cn.hutool.core.convert.Convert;
import org.junit.jupiter.api.Test;

/**
 * @author RainZiYu
 * @Date 2023/3/7
 */
public class NoSpringTest {
    @Test
    void testInt() {
        Integer integer = Convert.toInt("你好");
        System.out.println(integer);
    }
}
