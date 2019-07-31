package cn.bosenkeji.plugin;

import com.alibaba.fastjson.JSON;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
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
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER)
public class AliGatewayBackendOperationExtension implements OperationBuilderPlugin {
    private static final String X_ALIYUN_APIGATEWAY_BACKEND = "x-aliyun-apigateway-backend";
    private static final String X_ALIYUN_APIGATEWAY_BACKEND_TYPE = "type";
    private static final String X_ALIYUN_APIGATEWAY_VPC_ACCESS_NAME = "vpcAccessName";
    private static final String X_ALIYUN_APIGATEWAY_PATH = "path";
    private static final String X_ALIYUN_APIGATEWAY_METHOD = "method";
    private static final String X_ALIYUN_APIGATEWAY_TIMEOUT = "timeout";
    private static final String X_ALIYUN_APIGATEWAY_PARAMATER_HANDLING = "x-aliyun-apigateway-paramater-handling";
    private static final String X_ALIYUN_APIGATEWAY_CONSUMES = "consumes";

    @Override
    public void apply(OperationContext context) {

        ObjectVendorExtension extension1 = new ObjectVendorExtension(ensurePrefixed(X_ALIYUN_APIGATEWAY_BACKEND));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_BACKEND_TYPE, "HTTP-VPC"));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_VPC_ACCESS_NAME, "bs-ccr-test"));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_TIMEOUT, "10000"));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_PATH, context.requestMappingPattern()));
        extension1.addProperty(new StringVendorExtension(X_ALIYUN_APIGATEWAY_METHOD, context.httpMethod().name().toLowerCase()));

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
