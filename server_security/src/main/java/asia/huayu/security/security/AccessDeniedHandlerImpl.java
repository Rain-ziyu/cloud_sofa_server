package asia.huayu.security.security;

import asia.huayu.common.entity.Result;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.util.SystemEnums;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RainZiYu
 * @Date 2023/3/7
 * 当动态的判断到当前用户无权限时
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ResponseUtil.out(response, Result.NO_AUTH(SystemEnums.NO_AUTH.VALUE));
    }
}
