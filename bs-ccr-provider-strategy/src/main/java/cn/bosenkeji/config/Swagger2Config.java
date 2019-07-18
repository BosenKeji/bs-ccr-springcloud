package cn.bosenkeji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;


@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Resource
    SwaggerConfig swaggerConfig;
    @Bean
    public Docket createRestApi(){
        return swaggerConfig.createRestApi();
    }


}