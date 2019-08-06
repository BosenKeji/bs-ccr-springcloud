package cn.bosenkeji.plugin;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.http.HttpMethod;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.*;

/**
 * @ClassName SwaggerManualApiPlugin
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public  class SwaggerManualApiPlugin implements ApiListingScannerPlugin {

    public SwaggerManualApiPlugin(CachingOperationNameGenerator operationNames) {
        this.operationNames = operationNames;
    }

    @Override
    public boolean supports(DocumentationType delimiter) {
        return DocumentationType.SWAGGER_2.equals(delimiter);
    }

    private final CachingOperationNameGenerator operationNames;


    @Override
    public List<ApiDescription> apply(DocumentationContext context) {
        return new ArrayList<ApiDescription>(
                Arrays.asList(
                        new ApiDescription("/oauth/token", "Access token endpoint for client credentials grant type",
                                Arrays.asList(
                                        new OperationBuilder(operationNames)
                                                .authorizations(Arrays.asList(
                                                        new SecurityReference("BasicAuth", new AuthorizationScope[]{
                                                                new AuthorizationScope("read", "read access"),
                                                                new AuthorizationScope("write", "write access"),
                                                                new AuthorizationScope("internal", "internal access")})))
                                                .tags(new HashSet<String>(Arrays.asList("OAuth Controller")))
                                                .summary("token")
                                                .codegenMethodNameStem("credentials-grant-type-POST")
                                                .method(HttpMethod.POST)
                                                .notes("Access token endpoint for client credentials grant type.")
                                                .parameters(Arrays.asList(
                                                        new ParameterBuilder()
                                                                .description("Method for a client application to acquire an access token")
                                                                .type(new TypeResolver().resolve(String.class))
                                                                .name("grant_type")
                                                                .parameterType("formData")
                                                                .required(true)
                                                                .modelRef(new ModelRef("string"))
                                                                .build(),
                                                        new ParameterBuilder()
                                                                .description("Provide a way to limit the amount of access that is granted to an access token")
                                                                .type(new TypeResolver().resolve(String.class))
                                                                .name("scope")
                                                                .parameterType("formData")
                                                                .required(true)
                                                                .modelRef(new ModelRef("string"))
                                                                .build()))
                                                .responseModel(new ModelRef("string"))
                                                .build()), false)));

    }

}
