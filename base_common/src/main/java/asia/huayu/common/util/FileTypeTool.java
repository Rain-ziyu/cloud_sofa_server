package asia.huayu.common.util;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.HexUtil;

import java.io.InputStream;


/**
 * @author RainZiYu
 * @Date 2022/11/15
 */
public class FileTypeTool {
    public static String getFileType(byte[] in) {
        String type = FileTypeUtil.getType(HexUtil.encodeHexStr(in, false));
        return type;
    }

    public static String getFileType(InputStream in) {
        String type = FileTypeUtil.getType(in);
        return type;
    }
}
