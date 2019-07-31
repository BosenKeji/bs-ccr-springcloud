package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.handler.AnnotationHandler;
import cn.bosenkeji.annotation.handler.AnnotationProxy;
import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonSerializer;
import io.swagger.annotations.Api;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import io.swagger.models.parameters.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.apache.http.util.TextUtils.isEmpty;
import static springfox.documentation.swagger.common.HostNameProvider.componentsFrom;

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

    @Resource
    Docket docket;


    @Resource
    DocumentationCache documentationCache;

    @Resource
    ServiceModelToSwagger2Mapper mapper;


    @GetMapping("/get_route")
    public Result getRoute(HttpServletRequest servletRequest) throws Exception {
        Documentation documentation = documentationCache.documentationByGroup(docket.getGroupName());


        System.out.println(docket.getGroupName());

        Swagger swagger = mapper.mapDocumentation(documentation);
        Map<String, Path> map = swagger.getPaths();
        System.out.println(swagger);
        if (!map.isEmpty()){
            for (Map.Entry<String, Path> entry : map.entrySet()){
                System.out.println("getKey---->"+entry.getKey());
                List<Operation> operations =  entry.getValue().getOperations();
                if (!operations.isEmpty()){
                    for (Operation operation: operations){
                        System.out.println(operation.getDescription());
                    }
                }
            }
        }


//        System.out.println(JSON.toJSONString(swagger));

//        Class classType = Class.forName("cn.bosenkeji.controller.ConsumerCoinController");
//
//        Method[] methods = classType.getDeclaredMethods();
//
//        for (Method method : methods){
//
//
//
//            Annotation[] annotations = method.getAnnotations();
//            for (Annotation annotation: annotations){
//                AnnotationHandler annotationHandler = (new AnnotationProxy()).
//                        annotationHandler(annotation.annotationType());
//                String[] value = annotationHandler.getValue(method);
//                System.out.println(value[0]);
//
//            }
//            System.out.println(method.getName());
//        }
        return new Result();

    }
}
