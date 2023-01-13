package asia.huayu.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author RainZiYu
 * @Date 2022/12/10
 */
public class RequestUtil {
    /**
     * 方法<code>getRequest</code>作用为：
     * 获取本次请求的request
     *
     * @param
     * @return javax.servlet.http.HttpServletRequest
     * @throws
     * @author RainZiYu
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }
}
