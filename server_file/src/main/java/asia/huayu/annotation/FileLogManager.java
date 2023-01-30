package asia.huayu.annotation;

import java.lang.annotation.*;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 * 文件上传开启日志记录注解
 */
@Documented
// 运行时注解
@Retention(RetentionPolicy.RUNTIME)
// 作用域方法
@Target(ElementType.METHOD)
public @interface FileLogManager {
    /**
     * 方法<code>description</code>作用为：
     * 描述该上传文件类型
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String description() default "文件上传";

    /**
     * 方法fileNameParamLocation作用为：
     * 标识方法中有关文件名称的参数是第几个
     *
     * @param
     * @return int
     * @throws
     * @author RainZiYu
     */
    int fileNameParamLocation() default 0;
}
