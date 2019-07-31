package cn.bosenkeji.annotation.handler.Impl;

import cn.bosenkeji.annotation.handler.AnnotationHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * @ClassName GetMappingHandler
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class RequestMappingHandler implements AnnotationHandler {

    @Override
    public String[] getValue(Method method) {
        RequestMapping requestMapping = (RequestMapping) method.getDeclaredAnnotation(RequestMapping.class);
        return requestMapping.value();
    }
}
