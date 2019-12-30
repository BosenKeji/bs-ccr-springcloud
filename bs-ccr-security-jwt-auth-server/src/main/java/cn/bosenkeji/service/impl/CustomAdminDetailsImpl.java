package cn.bosenkeji.service.impl;

import cn.bosenkeji.service.PermissionGroupService;
import cn.bosenkeji.service.RoleService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.permission.PermissionGroup;
import cn.bosenkeji.vo.permission.RolePermissionGroup;
import cn.bosenkeji.vo.permission.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
public class CustomAdminDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -4139135931771626303L;

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
        /*
        //获取当前用户的角色
        List<UserRole> userRoleList = roleService.listUserRoleByUserIdAndType(admin.getId(), 1);
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
        return false;
    }
}
