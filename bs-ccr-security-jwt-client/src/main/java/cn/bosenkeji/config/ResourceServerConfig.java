package cn.bosenkeji.config;

import cn.bosenkeji.exception.AuthExceptionEntryPoint;
import cn.bosenkeji.exception.CustomAccessDeniedHandler;
import cn.bosenkeji.service.PermissionGroupService;
import cn.bosenkeji.vo.permission.Permission;
import cn.bosenkeji.vo.permission.PermissionGroupOther;
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
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private PermissionGroupService permissionGroupService;


    @Override
    /**
     * example:
     *      http.authorizeRequests().antMatchers("/employee").hasRole("ADMIN");
     */
    public void configure(final HttpSecurity http) throws Exception {

//        List<PermissionGroupOther> permissionGroupOtherList = permissionGroupService.listPermissionGroupJoin();

        //basic
        http.authorizeRequests().antMatchers("/login/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
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
                .antMatchers("/configuration/security").permitAll();

//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/strategy/").hasAuthority("manege")
//                .antMatchers(HttpMethod.GET,"/strategy_sequence/").hasAuthority("use");


        /*
        permissionGroupOtherList.forEach((g) -> {
            //按HttpMethod分组
            Map<Integer, List<Permission>> permissionMethodMap = g.getPermissionList().stream().collect(Collectors.groupingBy(Permission::getHttpMethod));

            permissionMethodMap.forEach((k,v) -> {
                switch (k) {
                    case 1 :
                        setUrl(http,HttpMethod.POST,v.stream().map(Permission::getUrl).toArray(String[]::new),g.getName());
                        break;
                    case 2 :
                        setUrl(http,HttpMethod.DELETE,v.stream().map(Permission::getUrl).toArray(String[]::new),g.getName());
                        break;
                    case 3 :
                        setUrl(http,HttpMethod.PUT,v.stream().map(Permission::getUrl).toArray(String[]::new),g.getName());
                        break;
                    case 4 :
                        setUrl(http,HttpMethod.GET,v.stream().map(Permission::getUrl).toArray(String[]::new),g.getName());
                    break;
                }
            });
        });
        */

        http.authorizeRequests().anyRequest().authenticated().and().formLogin().permitAll().and().csrf().disable();
    }

    private void setUrl(HttpSecurity httpSecurity, HttpMethod httpMethod, String[] urlArray,String authority) {
        try {
            httpSecurity.authorizeRequests().antMatchers(httpMethod,urlArray).hasAnyAuthority(authority);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("123");
        System.out.println(encode);
    }

}
