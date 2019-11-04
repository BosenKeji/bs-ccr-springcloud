package cn.bosenkeji.service.impl;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;

/**
 * @ClassName CustomAuthenticationProvider
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    /*
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();




        if (!name.isEmpty() && StringUtils.isEmpty(authentication.getCredentials())) {
            System.out.println("authentication.getCredentials()--->"+authentication.getCredentials());
            Authentication auth = new UsernamePasswordAuthenticationToken(name, "secret");

            return auth;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
    */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return super.authenticate(authentication);
    }
}
