package cn.bosenkeji.annotation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchCacheRemove {

    String[] value() default {};
    String unless() default "";
    String condition() default "";
}
