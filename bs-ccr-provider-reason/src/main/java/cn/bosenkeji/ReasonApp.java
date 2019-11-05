package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
public class ReasonApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ReasonApp.class,args);
    }
}
