package asia.huayu.aop;

import asia.huayu.annotation.FileLogManager;
import asia.huayu.common.util.AopUtil;
import asia.huayu.entity.File;
import asia.huayu.service.FileService;
import asia.huayu.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 * 保存文件上传切面
 */
@Aspect
@Slf4j
// 如果不将改切面类交给spring管理无法使用注入的FileService
@Component
public class UploadAspect {
    @Autowired
    FileService fileService;
    @Autowired
    MinioService minioService;

    // 只针对这个一个方法建立切面
    @Pointcut("execution(* asia.huayu.service.MinioService.upload(java.lang.String, java.lang.String, java.lang.String, java.io.InputStream, java.lang.Long))")
    public void uploadPoint() {
    }

    @Around(value = "uploadPoint()")
    public void uploadAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获取方法上的注解
        Signature signature = proceedingJoinPoint.getSignature();
        Method method = AopUtil.getMethod(proceedingJoinPoint);
        FileLogManager annotation = method.getAnnotation(FileLogManager.class);
        // 获取注解描述
        String description = annotation.description();
        // 获取所有方法参数
        Object[] args = proceedingJoinPoint.getArgs();
        String bucketName = args[1].toString();
        String fileName = args[2].toString();
        Long fileSize = (Long) args[4];
        // 执行上传逻辑
        proceedingJoinPoint.proceed();
        File file = new File();
        file.setFileName(fileName);
        file.setFileSize(fileSize);
        file.setFileUrl(minioService.getPreviewFileUrl(bucketName, fileName));
        file.setCreateTime(new Date());
        file.setModifyTime(new Date());
        boolean save = fileService.save(file);
    }
}
