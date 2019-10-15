package cn.bosenkeji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;


/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, EurekaClientAutoConfiguration.class})
@EnableDiscoveryClient
@MapperScan("cn.bosenkeji.mapper")
public class ProductComboApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(ProductComboApp.class,args);
        //initComboData();
    }


}
