package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableEurekaClient
@EnableDiscoveryClient
@EnableCircuitBreaker
public class BsCcrProviderProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BsCcrProviderProductApplication.class, args);
    }

}
