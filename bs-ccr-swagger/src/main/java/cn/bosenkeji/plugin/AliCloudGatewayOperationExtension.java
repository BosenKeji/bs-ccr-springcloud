package cn.bosenkeji.plugin;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ObjectVendorExtension;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Class.forName;

/**
 * @ClassName AliyunGatewayExtension
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 * @Example
 *      MOCK CODE:
 *          extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_TYPE, "MOCK"));;
 *          extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_MOCK_RESULT, mock_result));;
 *          extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_MOCK_STATUS_CODE, "200"));
 *
 **/
//@Component
//@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class AliCloudGatewayOperationExtension implements OperationBuilderPlugin {
    private static final String X_ALIYUN_APIGATEWAY_BACKEND = "x-aliyun-apigateway-backend";
    private static final String X_ALIYUN_APIGATEWAY_BACKEND_TYPE = "type";
    private static final String X_ALIYUN_APIGATEWAY_BACKEND_MOCK_RESULT = "mockResult";
    private static final String X_ALIYUN_APIGATEWAY_BACKEND_MOCK_STATUS_CODE = "mockStatusCode";
    private static final String X_ALIYUN_APIGATEWAY_PARAMATER_HANDLING = "x-aliyun-apigateway-paramater-handling";

    @Override
    public void apply(OperationContext context) {


        String[] groupNameArray = context.getGroupName().split("-");

        String mock_result;
        String mock_handler = "";
        if (groupNameArray.length > 0){
            for (String name: groupNameArray) {
                mock_handler += StringUtils.capitalize(name);
            }

        } else {
            mock_handler = context.getGroupName();
        }

        try {
            Class classType = forName("cn.bosenkeji.result.mock."+mock_handler+"ResultMock");
            Object obj = classType.newInstance();

            Method method = classType.getDeclaredMethod(context.getName());
            method.setAccessible(true);
            mock_result = (String) method.invoke(obj);
        } catch (Exception e) {
            mock_result = "OK";
        }

        ObjectVendorExtension extension1 = new ObjectVendorExtension(ensurePrefixed(X_ALIYUN_APIGATEWAY_BACKEND));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_TYPE, "MOCK"));;
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_MOCK_RESULT, mock_result));;
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_MOCK_STATUS_CODE, "200"));

        StringVendorExtension extension2 = new StringVendorExtension(
                ensurePrefixed(X_ALIYUN_APIGATEWAY_PARAMATER_HANDLING), "MAPPING");

        
        List<VendorExtension> extensions = new ArrayList<VendorExtension>();
        extensions.add(extension1);
        extensions.add(extension2);
        context.operationBuilder().extensions(extensions);
    }

    private Set<ResponseMessage> responseMessages() {
        return newHashSet(
                    new ResponseMessageBuilder().code(200).message("Successfully received bug 1767 or 2219 response")
                            .responseModel(new ModelRef("string")).build()
        );
    }


    private String ensurePrefixed(String name) {

        if (!isNullOrEmpty(name)) {
            if (!name.startsWith("x-")) {
                name = "x-" + name;
            }
        }
        return name;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return SwaggerPluginSupport.pluginDoesApply(delimiter);
    }
}
