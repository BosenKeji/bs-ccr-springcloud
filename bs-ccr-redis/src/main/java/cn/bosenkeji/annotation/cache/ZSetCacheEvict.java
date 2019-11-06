package cn.bosenkeji.annotation.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZSetCacheEvict {

    String key() default "";
    String value() default "";
    String unless() default "";
    String score() default "";

}
