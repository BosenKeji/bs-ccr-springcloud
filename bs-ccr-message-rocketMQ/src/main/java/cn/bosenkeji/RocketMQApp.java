package cn.bosenkeji;

import cn.bosenkeji.messaging.MySink;
import cn.bosenkeji.messaging.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;


/**
 * @Author CAJR
 * @create 2019/7/25 15:00
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableBinding({MySource.class, MySink.class})
public class RocketMQApp {

    public static void main(String []args){
        SpringApplication.run(RocketMQApp.class,args);
    }

}
