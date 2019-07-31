package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.util.AliCloudApiManageUtil;
import cn.bosenkeji.util.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
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

        Class classType = ICoinClientService.class;

        Method[] methods = classType.getDeclaredMethods();

        for (Method method : methods){
//            Annotation[][] annotations = method.getParameterAnnotations();
//            for (Annotation[] annotation : annotations){
//                for (Annotation ann: annotation){
//                    System.out.println(ann);
//                }
//            }
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation: annotations){
                    System.out.println("annotation--->"+JSON.toJSONString(annotation.annotationType().getTypeParameters()));
            }
            System.out.println(method.getName());
        }
        return new Result();

    }
}
