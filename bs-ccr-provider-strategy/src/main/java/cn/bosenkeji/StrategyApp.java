package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableCaching
public class StrategyApp {

    public static void main(String[] args) {
        SpringApplication.run(StrategyApp.class);
    }
}
