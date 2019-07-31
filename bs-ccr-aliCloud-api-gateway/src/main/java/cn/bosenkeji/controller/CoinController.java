package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.handler.AnnotationHandler;
import cn.bosenkeji.annotation.handler.AnnotationProxy;
import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import io.swagger.annotations.Api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import com.alibaba.fastjson.JSON;

import static java.lang.Class.forName;

/**
 * @ClassName CoinController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@Api(tags = "Coin 管理")
public class CoinController {
    @Resource
    AliCloudApiManageUtil aliCloudApiManageUtil;

    @GetMapping("/get_route")
    public Result getRoute() throws Exception {

        Class classType = Class.forName("cn.bosenkeji.service.ICoinClientService");

        Method[] methods = classType.getDeclaredMethods();

        for (Method method : methods){



            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation: annotations){
                AnnotationHandler annotationHandler = (new AnnotationProxy()).annotationHandler(annotation.annotationType());
                String[] value = annotationHandler.getValue(method);
                System.out.println(value[0]);

            }
            System.out.println(method.getName());
        }
        return new Result();

    }
}
