package asia.huayu.common.exception.handler;


import asia.huayu.common.entity.Result;
import asia.huayu.common.exception.ServiceProcessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.sql.SQLException;

/**
 * @author RainZiYu
 * @Date 2022/11/14
 */
@RestControllerAdvice
@Slf4j
public class ServiceProcessExceptionHandler {
    // 注解：出现异常会来到这个方法处理
    // 参数：捕获控制器出现的异常，可传入集合捕获多种类型的异常
    @ExceptionHandler(ServiceProcessException.class)
    public Result handlerError(ServiceProcessException ex, HandlerMethod hm) {
        log.info("以下是自定义抛出的异常");
        // 异常信息
        log.error("异常信息为：" + ex.getMessage());
        // 哪个类下
        log.error("产生异常的类为:" + hm.getBean().getClass());
        // 在哪个方法的
        log.error("产生异常的方法为:" + hm.getMethod().getName());
        return Result.ERROR(500, ex.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public Result handlerSQLException(SQLException ex, HandlerMethod hm) {
        log.info("以下是SQL抛出的异常");
        // 异常信息
        log.error("异常信息为：" + ex.getMessage());
        // 哪个类下
        log.error("产生异常的类为:" + hm.getBean().getClass());
        // 在哪个方法的
        log.error("产生异常的方法为:" + hm.getMethod().getName());
        return Result.ERROR(500, "执行SQL出现异常");
    }

    // 兜底异常处理
    @ExceptionHandler(Exception.class)
    public Result handlerException(Exception ex, HandlerMethod hm) {
        log.info("以下是抛出的异常");
        // 异常信息
        log.error("异常信息为：" + ex.getMessage());
        // 哪个类下
        log.error("产生异常的类为:" + hm.getBean().getClass());
        // 在哪个方法的
        log.error("产生异常的方法为:" + hm.getMethod().getName());
        return Result.ERROR(500, ex.getMessage());
    }
}
