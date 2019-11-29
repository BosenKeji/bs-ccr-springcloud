package cn.bosenkeji.annotation.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchCacheRemove {

    /**
     * 多个要清空的 前缀
     * @return
     */
    String[] value() default {};

    /**
     *  不执行 模糊清空缓存 的条件
     * @return
     */
    String unless() default "";

    /**
     * 执行 模糊清空缓存的条件
     * @return
     */
    String condition() default "";
}
