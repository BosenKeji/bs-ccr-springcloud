package cn.bosenkeji.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName Ribbon
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class RibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new com.netflix.loadbalancer.RandomRule();
    }
}
