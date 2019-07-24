package cn.bosenkeji.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotExistByFiled {
    //默认错误消息
    String message() default "{forbidden.word}";
    //查询的表名
    String tableName() default "";
    String cloumn() default "";
    String sql() default "";

}
