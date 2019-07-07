package cn.bosenkeji;

import cn.bosenkeji.config.RibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName App
 * @Description 启动类 包含 Eureka Ribbon Feign
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "BS-CCR-PROVIDER-COIN", configuration = RibbonConfig.class)
@EnableFeignClients("cn.bosenkeji.service")
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }
}
