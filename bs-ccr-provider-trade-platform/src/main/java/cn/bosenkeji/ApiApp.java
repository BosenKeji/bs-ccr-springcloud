package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author CAJR
 * @create 2019/7/15 9:27
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EurekaClientAutoConfiguration.class})
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients("cn.bosenkeji.service")
public class ApiApp {
    public static void main(String []args){
        SpringApplication.run(ApiApp.class,args);
    }
}
