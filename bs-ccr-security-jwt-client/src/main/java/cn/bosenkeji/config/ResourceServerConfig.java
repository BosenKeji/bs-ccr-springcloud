package cn.bosenkeji.config;

import cn.bosenkeji.exception.AuthExceptionEntryPoint;
import cn.bosenkeji.exception.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

/**
 * @ClassName ResourceServerConfig
 * @Description 资源服务
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    /**
     * example:
     *      http.authorizeRequests().antMatchers("/employee").hasRole("ADMIN");
     */
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login/**").permitAll()
                .antMatchers("/oauth/token/**").permitAll()
                .antMatchers("/tokens/**").permitAll()
                .antMatchers(HttpMethod.POST, "/user/").permitAll()
                .antMatchers(HttpMethod.POST, "/admin/").permitAll()
                .antMatchers(HttpMethod.PUT,"/user/forget_password").permitAll()
                //swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/configuration/ui").permitAll()
                .antMatchers("/configuration/security").permitAll()

                //Admin  管理员用户、产品、用户套餐、交易平台等接口
//                .antMatchers(
//                        "/product**/**"
//                        ,"/user_product_combo**/**"
//                        ,"/admin/**"
//                        ,"/trade_platform**/**"
//                )
//                .hasAuthority("ADMIN")

                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().csrf().disable();
    }


    @Override
    public void configure(final ResourceServerSecurityConfigurer config) {
        config.tokenServices(tokenServices());
        config.authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(accessDeniedHandler);
    }


    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        converter.setSigningKey("123");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
