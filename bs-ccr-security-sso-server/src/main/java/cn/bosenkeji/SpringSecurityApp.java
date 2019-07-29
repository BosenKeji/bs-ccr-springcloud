package cn.bosenkeji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @ClassName SpringSecurityApp
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@SpringBootApplication
@EnableResourceServer
public class SpringSecurityApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApp.class, args);
    }
}
