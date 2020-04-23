package cn.bosenkeji;

import cn.bosenkeji.config.RobotRunStatusSink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author xivin
 * @email 1250402127@qq.com
 * @description
 * @date 2020/4/23
 */
@SpringBootApplication
@EnableBinding({ RobotRunStatusSink.class })
public class UsMqConsumerApp {

    public static void main(String[] args) {
        SpringApplication.run(UsMqConsumerApp.class,args);
    }

}
