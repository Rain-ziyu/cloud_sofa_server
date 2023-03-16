package asia.huayu.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * @author RainZiYu
 * @Date 2023/3/14
 * 仅适用于单机模式获取id的唯一性保证
 */
public class IdUtils {
    private static Snowflake snowflake = IdUtil.getSnowflake();

    /**
     * 生成long 类型的ID
     *
     * @return
     */
    public static Long getId() {
        return snowflake.nextId();
    }

    /**
     * 生成String 类型的ID
     *
     * @return
     */
    public static String getIdStr() {
        return snowflake.nextIdStr();
    }


}
