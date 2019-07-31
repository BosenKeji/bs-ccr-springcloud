package cn.bosenkeji.annotation.handler.Impl;

import cn.bosenkeji.annotation.handler.AnnotationHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;

/**
 * @ClassName GetMappingHandler
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class PostMappingHandler implements AnnotationHandler {

    @Override
    public String[] getValue(Method method) {
        PostMapping postMapping = (PostMapping) method.getDeclaredAnnotation(PostMapping.class);
        return postMapping.value();
    }
}
