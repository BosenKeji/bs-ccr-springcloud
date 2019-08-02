package cn.bosenkeji.controller;

import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.cloudapi.model.v20160714.CreateApiRequest;
import com.aliyuncs.cloudapi.model.v20160714.DescribeApiResponse;
import io.swagger.annotations.Api;
import io.swagger.models.HttpMethod;
import io.swagger.models.Operation;
import io.swagger.models.Path;
import io.swagger.models.Swagger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
                CreateApiRequest request = new CreateApiRequest();

                request.setDescription(String.valueOf(swagger.getInfo()));

                /*把路径的'{}'替换成'[]'*/
                String path = entry.getKey().replace('{', '[').replace('}', ']');


                System.out.println("getKey---->"+entry.getKey());
                List<Operation> operations =  entry.getValue().getOperations();

                System.out.println(JSON.toJSONString(entry.getValue().getOperationMap().get(HttpMethod.GET)));

                Map<HttpMethod,Operation> operationMap = entry.getValue().getOperationMap();
                Object [] httMethods = entry.getValue().getOperationMap().keySet().toArray();
                if (!operationMap.isEmpty()){
                    for (int i=0;i<httMethods.length;i++){
                        Operation operation = operationMap.get(httMethods[i]);

                        System.out.println("Path-->"+path+"|httpMethod-->"+httMethods   [i]);
                        System.out.println(JSONObject.toJSONString(operation));
                    }
                }

//                if (!operations.isEmpty()){
//                    for (int i=0;i<operations.size();i++){
//                        Operation operation = operations.get(i);
//
//                        Object [] httMethodStr = entry.getValue().getOperationMap().keySet().toArray();
//                        System.out.println("Path-->"+path+"|httpMethod-->"+httMethodStr[i]);
//
//                        request.setApiName(operation.getOperationId());
//                        request.setDescription(operation.getSummary());
//
//                        /*set一个requestMode参数*/
//                        requestConfig.setRequestMode("MAPPING");
//
//                        serviceConfig.setServiceHttpMethod(String.valueOf(httMethodStr[i]));
//                        requestConfig.setRequestHttpMethod(String.valueOf(httMethodStr[i]));
//
////                        System.out.println(operation.getDescription());
//                    }
//                }
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
