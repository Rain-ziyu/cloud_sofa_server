import asia.huayu.entity.User;
import cn.hutool.core.util.NumberUtil;
import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;

/**
 * @author RainZiYu
 * @Date 2023/1/12
 */
public class TestHashCode {
    @Test
    public void testHashCode() {
        User user = new User();
        user.setId("1");
        user.setSalt("aaa");
        System.out.println(user.hashCode());
        System.out.println(NumberUtil.getBinaryStr(user.hashCode()));

    }

    @Test
    public void testJSON() {
        String aaaaa = JSON.toJSONString("aaaaa");
        System.out.println(aaaaa);

    }
}
