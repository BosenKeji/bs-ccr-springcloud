package cn.bosenkeji;

import cn.bosenkeji.config.RobotRunStatusSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
@EnableFeignClients("cn.bosenkeji.service")
@EnableCaching
@EnableBinding({ RobotRunStatusSource.class })
public class ComboApp {

    public static void main(String[] args) {
        SpringApplication.run(ComboApp.class, args);
    }

}
