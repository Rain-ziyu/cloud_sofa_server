import asia.huayu.entity.User;
import asia.huayu.security.util.SystemEnums;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.crypto.digest.MD5;
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

    @Test
    public void testMD5() {
        MD5 md5 = MD5.create();
        String wanwenlong521 = md5.digestHex("wanwenlong521");
        System.out.println(wanwenlong521);
    }

    @Test
    public void testEnum() {
        System.out.println(SystemEnums.AUTH_NAME.name());
        // 可以重写toString方法来达到输出中文的目的
        System.out.println(SystemEnums.AUTH_NAME.toString());
    }
}
