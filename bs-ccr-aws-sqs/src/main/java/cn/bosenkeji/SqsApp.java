package cn.bosenkeji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author CAJR
 * @create 2019/8/19 17:39
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SqsApp {
    public static void main(String[] args) {
        SpringApplication.run(SqsApp.class,args);
    }
}
