package cn.bosenkeji.service.impl;

import cn.bosenkeji.service.PermissionGroupService;
import cn.bosenkeji.service.RoleService;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.permission.PermissionGroup;
import cn.bosenkeji.vo.permission.RolePermissionGroup;
import cn.bosenkeji.vo.permission.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CustomUserDetailsImpl
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Service
public class CustomUserDetailsImpl implements UserDetails {

    private transient RoleService roleService;

    private transient PermissionGroupService permissionGroupService;

    private User user;

    public CustomUserDetailsImpl() { }

    CustomUserDetailsImpl(User user) {
        this.user = user;
    }

    CustomUserDetailsImpl(User user, RoleService roleService, PermissionGroupService permissionGroupService) {
        this.user = user;
        this.roleService = roleService;
        this.permissionGroupService = permissionGroupService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        /*
        //获取当前用户的角色
        List<UserRole> userRoleList = roleService.listUserRoleByUserIdAndType(user.getId(), 2);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userRoleList)) {

            //获取角色的ids
            List<Integer> roleIds = userRoleList.stream().map(UserRole::getRoleId).distinct().collect(Collectors.toList());
            //获取角色对应的权限组
            List<RolePermissionGroup> rolePermissionGroupList = roleService.listRolePermissionGroupByRoleIds(roleIds);
            //获取权限中ids
            List<Integer> groupIds = rolePermissionGroupList.stream().map(RolePermissionGroup::getPermissionGroupId).distinct().collect(Collectors.toList());
            //获取所有的权限组
            List<PermissionGroup> permissionGroupList = permissionGroupService.listPermissionGroupByIds(groupIds);

            //设置用户拥有的角色对应的权限组
            permissionGroupList.stream().map(PermissionGroup::getName).forEach((p) -> {
                authorities.add(new SimpleGrantedAuthority(p));
            });

        } else {
            //如果没有设置角色，添加默认权限
            authorities.add(new SimpleGrantedAuthority("default"));
        }
        */
        final List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getTel();
    }

    public int getId() { return user.getId(); }

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
        if (user.getStatus() >= 1){
            return true;
        }
        return true;
    }
}
