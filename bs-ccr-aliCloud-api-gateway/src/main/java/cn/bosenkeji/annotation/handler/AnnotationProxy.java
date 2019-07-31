package cn.bosenkeji.annotation.handler;

import cn.bosenkeji.annotation.handler.Impl.GetMappingHandler;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * @ClassName AnnotationProxy
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class AnnotationProxy {
    public AnnotationHandler annotationHandler(Class clazz) throws Exception{

        Class handlerClass = Class.forName("cn.bosenkeji.annotation.handler.Impl."+clazz.getSimpleName()+"Handler");

        AnnotationHandler annotationHandler = (AnnotationHandler)handlerClass.newInstance();

        return annotationHandler;

    }
}
