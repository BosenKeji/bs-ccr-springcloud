package cn.bosenkeji.service.impl;

import cn.bosenkeji.service.PermissionGroupService;
import cn.bosenkeji.service.RoleService;
import cn.bosenkeji.vo.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @ClassName CustomUserDetailsImpl
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class CustomAdminDetailsImpl implements UserDetails {

    private transient RoleService roleService;

    private transient PermissionGroupService permissionGroupService;

    private Admin admin;

    CustomAdminDetailsImpl(Admin admin) {
        this.admin = admin;
    }

    CustomAdminDetailsImpl(Admin admin, RoleService roleService, PermissionGroupService permissionGroupService) {
        this.admin = admin;
        this.roleService = roleService;
        this.permissionGroupService = permissionGroupService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getAccount();
    }

    public int getId() { return admin.getId(); }

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
        if (admin.getStatus() >= 1){
            return true;
        }
        return true;
    }
}
