package asia.huayu.annotation;

import java.lang.annotation.*;

/**
 * @author User
 * 访问限制注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

public @interface AccessLimit {

    int seconds();

    int maxCount();
}
