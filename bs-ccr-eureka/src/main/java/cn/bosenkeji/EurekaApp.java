package cn.bosenkeji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @ClassName EurekaApp
 * @Description 启动类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class,args);
    }
}
