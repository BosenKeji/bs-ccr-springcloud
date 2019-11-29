package cn.bosenkeji.annotation.cache;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZSetCacheEvict {

    /**
     * Zset key name
     * @return
     */
    String key() default "";

    /**
     * Zset value
     * @return
     */
    String value() default "";

    /**
     * undo
     * @return
     */
    String unless() default "";

    /**
     * Zset score
     * @return
     */
    String score() default "";

}
