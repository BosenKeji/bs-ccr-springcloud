package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @ClassName App
 * @Description 启动类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableEurekaClient
public class CoinApp {
    public static void main(String[] args) {
        SpringApplication.run(CoinApp.class, args);
    }
}
