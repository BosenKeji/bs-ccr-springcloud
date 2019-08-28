package cn.bosenkeji.annotation;

import java.lang.annotation.*;

/**
 * @Author CAJR
 * @create 2019/8/28 14:42
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenUser {

}
