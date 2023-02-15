package asia.huayu.security.security;


import asia.huayu.common.entity.Result;
import asia.huayu.common.util.ResponseUtil;
import asia.huayu.security.util.SystemEnums;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnauthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtil.out(httpServletResponse, Result.ERROR(SystemEnums.TOKEN_ERROR.VALUE));
    }
}
