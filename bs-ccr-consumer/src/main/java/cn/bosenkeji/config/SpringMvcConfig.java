package cn.bosenkeji.config;

import cn.bosenkeji.annotation.resolver.TokenUserAnnotationResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/8/28 15:48
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new TokenUserAnnotationResolver());
    }
}
