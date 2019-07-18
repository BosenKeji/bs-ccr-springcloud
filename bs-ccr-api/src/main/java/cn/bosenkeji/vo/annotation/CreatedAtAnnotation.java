package cn.bosenkeji.vo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @ClassName CreatedAtAnnotation
 * @Description 添加时间注解
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.FIELD})
public @interface CreatedAtAnnotation {
    String value() default "";
}
