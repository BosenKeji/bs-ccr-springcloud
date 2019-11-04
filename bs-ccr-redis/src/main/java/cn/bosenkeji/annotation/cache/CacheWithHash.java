package cn.bosenkeji.annotation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheWithHash {

    String key() default "";

    String name() default "";

    String operation() default "";

    String value() default "";

    String unless() default "";
}
