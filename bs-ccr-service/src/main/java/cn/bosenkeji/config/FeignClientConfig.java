package cn.bosenkeji.config;

import feign.Logger;
import feign.Request;
import feign.Retryer;
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

//@Configuration
public class FeignClientConfig {
    public static int connectTimeOutMillis = 10000;//超时时间
    public static int readTimeOutMillis = 10000;
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer() {
        return new Retryer.Default();
    }
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
