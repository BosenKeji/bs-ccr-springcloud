package cn.bosenkeji;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DealApp {

    public static void main(String[] args) {
        SpringApplication.run(DealApp.class, args);
    }
}
