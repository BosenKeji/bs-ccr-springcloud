package cn.bosenkeji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).extensions(getExtension())
                .apiInfo(getApiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("cn.bosenkeji.controller"))
                    .paths(PathSelectors.any())
                    .build()
                ;
    }

    /**
     * @Description 增加顶级扩展，ListVendorExtension表示以列表形式
     * @Author Yu XueWen
     * @return List<VendorExtension>
     */
    private List<VendorExtension> getExtension(){

        List<VendorExtension> vendorExtensionsList = new ArrayList<>();

        List<String> stringList = new ArrayList<>();
        stringList.add("http");

        VendorExtension listVendorExtension = new ListVendorExtension<String>("schemes", stringList);

        vendorExtensionsList.add(listVendorExtension);

        return vendorExtensionsList;
    }

    private ApiInfo getApiInfo() {
        // 定义联系人信息
        Contact contact = new Contact("YuXueWen","https://github.com/xiaoemoxiw", "yuxuewen23@qq.com");
        return new ApiInfoBuilder()
                .title("博森CCR系统") // 标题
                .description("博森CCR系统API信息") // 描述信息
                .version("1.0.0") // //版本
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }
}
