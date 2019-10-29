package cn.bosenkeji.annotation.cache;

import java.lang.annotation.*;

/**
 * redis 类型为 zset 的缓存
 * 优点： 有序、可以通过score删除对应的key
 * 缺点： score 只能保存数字类型 数据
 *
 * 建议使用范围： 用于 条件+主键的缓存
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ZSetCacheable {

    String key() default "";
    String value() default "";
    String score() default "";
    String unless() default "";
}
