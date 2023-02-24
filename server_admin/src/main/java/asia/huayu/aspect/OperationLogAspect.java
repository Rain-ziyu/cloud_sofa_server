package asia.huayu.aspect;

import asia.huayu.annotation.OptLog;
import asia.huayu.common.util.IpUtil;
import asia.huayu.entity.OperationLog;
import asia.huayu.entity.UserInfo;
import asia.huayu.event.OperationLogEvent;
import asia.huayu.service.UserInfoService;
import asia.huayu.util.UserUtil;
import com.alibaba.fastjson2.JSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.tags.Tag;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class OperationLogAspect {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UserInfoService userInfoService;

    @Pointcut("@annotation(asia.huayu.annotation.OptLog)")
    public void operationLogPointCut() {
    }

    @AfterReturning(value = "operationLogPointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public void saveOperationLog(JoinPoint joinPoint, Object keys) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        OperationLog operationLog = new OperationLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Tag tag = (Tag) signature.getDeclaringType().getAnnotation(Tag.class);
        Operation apiOperation = method.getAnnotation(Operation.class);
        OptLog optLog = method.getAnnotation(OptLog.class);
        operationLog.setOptModule(tag.getName());
        operationLog.setOptType(optLog.optType());
        operationLog.setOptDesc(apiOperation.description());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        methodName = className + "." + methodName;
        operationLog.setRequestMethod(Objects.requireNonNull(request).getMethod());
        operationLog.setOptMethod(methodName);
        if (joinPoint.getArgs().length > 0) {
            if (joinPoint.getArgs()[0] instanceof MultipartFile) {
                operationLog.setRequestParam("file");
            } else {
                operationLog.setRequestParam(JSON.toJSONString(joinPoint.getArgs()));
            }
        }
        String name = UserUtil.getAuthentication().getName();
        operationLog.setResponseData(JSON.toJSONString(keys));
        UserInfo user = userInfoService.getUserInfoByName(name);
        operationLog.setUserId(user.getId());
        operationLog.setNickname(user.getNickname());
        String ipAddress = IpUtil.getIpAddress(request);
        operationLog.setIpAddress(ipAddress);
        operationLog.setIpSource(IpUtil.getIpSource(ipAddress));
        operationLog.setOptUri(request.getRequestURI());
        applicationContext.publishEvent(new OperationLogEvent(operationLog));
    }

}
