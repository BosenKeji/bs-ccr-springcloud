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

    /**
     * 对应的键 名称
     * @return
     */
    String key() default "";

    /**
     * 值
     * @return
     */
    String value() default "";

    /**
     * 分数
     * @return
     */
    String score() default "";

    /**
     * 不缓存条件
     * @return
     */
    String unless() default "";
}
