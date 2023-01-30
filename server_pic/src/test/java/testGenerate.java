import asia.huayu.generate.GeneratePic;
import org.junit.Test;

import java.awt.*;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
public class testGenerate {
    @Test
    public void testGeneratePic() {
        GeneratePic.setFont(new Font("宋体", Font.BOLD, 50));
        GeneratePic.create("H", "D:\\PrivateProject\\cloud_sofa_server\\server_pic\\src\\main\\resources");
    }
}
