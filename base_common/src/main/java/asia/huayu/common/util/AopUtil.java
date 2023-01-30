package asia.huayu.common.util;

import com.alibaba.fastjson2.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
public class AopUtil {
    /**
     * 获取切点处的方法
     *
     * @param joinPoint
     * @return
     */
    public static Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return method;
    }

    /**
     * 将参数数组转化为字符串
     *
     * @param params 参数数组
     * @return 参数字符串
     */
    public static String getStringOfParams(Object[] params) {
        if (params.length <= 0 || params.length > 1024 || null == params) {
            return "";
        }

        StringBuffer paramString = new StringBuffer("参数:   ");
        for (Object param : params) {
            // 将参数转换成字符串
            String s = JSON.toJSONString(param);
            paramString.append(s).append("||");
        }
        return paramString.toString();
    }

    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public static Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }
}


