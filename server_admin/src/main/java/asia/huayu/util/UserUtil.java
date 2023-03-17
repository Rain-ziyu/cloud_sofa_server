package asia.huayu.util;


import cn.hutool.core.util.ObjectUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 方法userIsLogin作用为：
     * 判断用户是否登录 true登录 false未登录
     *
     * @param
     * @return java.lang.Boolean
     * @throws
     * @author RainZiYu
     */
    public static Boolean userIsLogin() {
        Authentication authentication = getAuthentication();
        if (ObjectUtil.isNull(authentication) || authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        } else {
            return true;
        }
    }

}
