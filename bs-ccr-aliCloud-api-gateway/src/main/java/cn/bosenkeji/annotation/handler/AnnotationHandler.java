package cn.bosenkeji.annotation.handler;

import java.lang.reflect.Method;

/**
 * @ClassName AnnotationHandler
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
public interface AnnotationHandler {
    public String[] getValue(Method method);

}
