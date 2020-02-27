package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.bosenkeji.mapper")
@EnableFeignClients("cn.bosenkeji.service")
public class ProductComboApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductComboApp.class,args);

    }


}
