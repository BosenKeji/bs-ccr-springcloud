package cn.bosenkeji.config;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignClientConfig
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/

@Configuration
public class FeignClientConfig {
//    @Bean
//    public BasicAuthRequestInterceptor getBasicAuthRequestInterceptor() {
//        return new BasicAuthRequestInterceptor("admin", "admin");
//    }
//
//    @Bean
//    public Logger.Level getFeignLoggerLevel() {
//        return feign.Logger.Level.FULL ;
//    }
}
