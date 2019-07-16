package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author CAJR
 * @create 2019/7/15 9:27
 */
@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ApiApp {
    public static void main(String []args){
        SpringApplication.run(ApiApp.class,args);
    }
}
