package cn.bosenkeji;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DealApp {

    public static void main(String[] args) {
        SpringApplication.run(DealApp.class, args);
    }
}
