package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @ClassName App
 * @Description 启动类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableTransactionManagement
@EnableAuthorizationServer
public class JwtAuthServerApp {
    public static void main(String[] args) {
        SpringApplication.run(JwtAuthServerApp.class, args);
    }
}
