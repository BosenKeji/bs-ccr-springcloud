package cn.bosenkeji.vo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @ClassName JwtUser
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-03 19:30
 * @Version 1.0
 */

public class JwtUser implements UserDetails {


    private int id;
    private String username;
    private String password;

    private String tel;
    private int status;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() { }

    public JwtUser(int id, String username, String password, String tel, int status, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.status = status;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
