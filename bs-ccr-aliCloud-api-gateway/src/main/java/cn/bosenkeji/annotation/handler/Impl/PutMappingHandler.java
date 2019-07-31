package cn.bosenkeji.annotation.handler.Impl;

import cn.bosenkeji.annotation.handler.AnnotationHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.lang.reflect.Method;

/**
 * @ClassName GetMappingHandler
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class PutMappingHandler implements AnnotationHandler {

    @Override
    public String[] getValue(Method method) {
        PutMapping putMapping = (PutMapping) method.getDeclaredAnnotation(PutMapping.class);
        return putMapping.value();
    }
}
