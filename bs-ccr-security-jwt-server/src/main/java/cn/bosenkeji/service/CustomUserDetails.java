package cn.bosenkeji.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Collections;

/**
 * @ClassName CustomUserDetails
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class CustomUserDetails extends User {
    public CustomUserDetails(String username, String password) {
        super(username, password, Collections.EMPTY_SET);
    }

    public CustomUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, Collections.EMPTY_SET);
    }
}
