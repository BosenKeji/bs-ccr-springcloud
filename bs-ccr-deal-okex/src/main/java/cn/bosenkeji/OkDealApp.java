package cn.bosenkeji;


import cn.bosenkeji.messaging.MySink;
import cn.bosenkeji.messaging.MySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding({MySource.class, MySink.class})
public class OkDealApp {

    public static void main(String[] args) {
        SpringApplication.run(OkDealApp.class, args);
    }
}
