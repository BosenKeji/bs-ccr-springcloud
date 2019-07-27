package cn.bosenkeji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableCaching
//@EnableDiscoveryClient
//@EnableCircuitBreaker
public class RedisApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(RedisApp.class, args);
    }
}
