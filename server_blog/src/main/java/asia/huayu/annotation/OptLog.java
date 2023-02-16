package asia.huayu.annotation;

import java.lang.annotation.*;

/**
 * @author User
 * 自定义日志记录注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OptLog {

    String optType() default "";
}
