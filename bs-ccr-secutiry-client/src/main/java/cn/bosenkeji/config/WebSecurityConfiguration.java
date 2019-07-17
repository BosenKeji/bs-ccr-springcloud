package cn.bosenkeji.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @ClassName WebSecurityConfiguration
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Configuration
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**").authorizeRequests()
            .antMatchers("/", "/login**").permitAll()
            .anyRequest().authenticated();
    }

}
