package cn.bosenkeji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;


/**
 * @Author xivinChen
 * @create 2019/7/15 16:00
 */
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
