package asia.huayu.common.aop;

import asia.huayu.common.annotation.LoggerManager;
import asia.huayu.common.util.AopUtil;
import asia.huayu.common.util.RequestUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 */
@Aspect
@Slf4j
@Component
// order越小该切面越先执行，并且晚结束
// @Order(value = 0)
public class LoggerAdvice {
    /**
     * 定义一个切点，在注解处切入
     */
    @Pointcut("@annotation(asia.huayu.common.annotation.LoggerManager)")
    public void aroundPointCut() {
    }

    /**
     * 方法<code>aroundAdvice</code>作用为：
     * 环绕切点aroundPointCut()的前后进行执行
     *
     * @param joinPoint
     * @return void
     * @throws
     * @author RainZiYu
     */
    @Around(value = "aroundPointCut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取切点方法
        Method method = AopUtil.getMethod(joinPoint);
        // 获取切点方法上的注解
        LoggerManager annotation = method.getAnnotation(LoggerManager.class);
        // 获取注解中填写的描述
        String description = annotation.description();
        // 获取切点方法的包名
        Signature signature = joinPoint.getSignature();
        // 获取切点方法的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取切点方法的方法名称
        String methodName = method.getName();
        // 获取方法上的参数信息
        Object[] methodArgs = joinPoint.getArgs();
        // 开始打印日志
        log.info("执行==={}===开始", description);
        log.info("方法名：{},类名：{},包名：{}", methodName, className, signature.toString());
        log.info("方法参数有：{}", JSONUtil.toJsonStr(methodArgs));
        // 如果注解携带参数要求记录请求内容则打印
        if (annotation.isRequest()) {
            // 获取当前请求的http信息
            HttpServletRequest request = RequestUtil.getRequest();
            // 获取本次请求的参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            // 将得到的本次请求的参数转化为对应的字符串
            Map<String, String> stringStringMap = AopUtil.converMap(parameterMap);
            log.info("本次请求参数有：{}", JSON.toJSONString(stringStringMap));
        }
        // 执行方法体
        Object proceed = joinPoint.proceed();
        log.info("执行==={}===结束", description);
        return proceed;
    }

    // @Before(value = "aroundPointCut()")
    public void beforeAdvice(JoinPoint joinPoint) throws Throwable {
        // 获取切点方法
        Method method = AopUtil.getMethod(joinPoint);
        // 获取切点方法上的注解
        LoggerManager annotation = method.getAnnotation(LoggerManager.class);
        // 获取注解中填写的描述
        String description = annotation.description();
        // 获取切点方法的包名
        Signature signature = joinPoint.getSignature();
        // 获取切点方法的类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取切点方法的方法名称
        String methodName = method.getName();
        // 获取方法上的参数信息
        Object[] methodArgs = joinPoint.getArgs();
        // 开始打印日志
        log.info("执行==={}===开始", description);
        log.info("方法名：{},类名：{},包名：{}", methodName, className, signature.toString());
        log.info("方法参数有：{}", JSONUtil.toJsonStr(methodArgs));
    }
}
