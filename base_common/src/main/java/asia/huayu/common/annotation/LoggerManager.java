package asia.huayu.common.annotation;

import java.lang.annotation.*;

/**
 * @author RainZiYu
 * @Date 2023/1/30
 * 全局日志打印注解
 */
@Documented
// 运行时注解
@Retention(RetentionPolicy.RUNTIME)
// 作用域方法
@Target(ElementType.METHOD)
public @interface LoggerManager {
    /**
     * 方法<code>description</code>作用为：
     * 描述该方法执行的操作
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author RainZiYu
     */
    String description() default "LoggerManager日志记录";

    /**
     * 方法<code>isRequest</code>作用为：
     * 是否需要记录本次方法执行的request信息
     *
     * @param
     * @return boolean
     * @throws
     * @author RainZiYu
     */
    boolean isRequest() default false;
}
