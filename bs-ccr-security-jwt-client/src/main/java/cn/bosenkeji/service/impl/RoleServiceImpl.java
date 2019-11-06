package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.RoleMapper;
import cn.bosenkeji.mapper.RolePermissionGroupMapper;
import cn.bosenkeji.mapper.UserRoleMapper;
import cn.bosenkeji.service.RoleService;
import cn.bosenkeji.vo.permission.Role;
import cn.bosenkeji.vo.permission.RolePermissionGroup;
import cn.bosenkeji.vo.permission.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionGroupMapper rolePermissionGroupMapper;

    @Override
    public Optional<Integer> saveRole(Role role) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        role.setCreatedAt(timestamp);
        role.setUpdatedAt(timestamp);
        int result = roleMapper.insertSelective(role);
        return Optional.of(result);
    }


    @Override
    public List<Role> listRole(Integer pageNum, Integer pageSize) {
        return roleMapper.listRole();
    }

    @Override
    public Optional<Role> getRoleById(Integer id) {
        Role role = roleMapper.getRoleById(id);
        return Optional.of(role);
    }

    @Override
    public Optional<Integer> updateRoleById(Role role) {
        role.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = roleMapper.updateBySelective(role);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deleteRoleById(Integer id) {
        Role role = new Role();
        role.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        role.setId(id);
        role.setStatus(0);
        int result = roleMapper.updateBySelective(role);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> saveUserRole(UserRole userRole) {
        userRole.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = userRoleMapper.insertSelective(userRole);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> updateUserRole(UserRole userRole) {
        userRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = userRoleMapper.updateBySelective(userRole);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deleteUserRole(Integer id) {
        UserRole userRole = new UserRole();
        userRole.setId(id);
        userRole.setStatus(0);
        userRole.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = userRoleMapper.updateBySelective(userRole);
        return Optional.of(result);
    }

    @Override
    public Optional<UserRole> getUserRole(Integer id) {
        UserRole userRole = userRoleMapper.getUserRoleById(id);
        return Optional.of(userRole);
    }

    @Override
    public List<UserRole> listUserRole() {
        return userRoleMapper.listUserRole();
    }

    @Override
    public List<UserRole> listUserRoleByUserIdAndType(Integer userId, Integer type) {
        return userRoleMapper.listUserRoleByUserIdAndType(userId,type);
    }

    @Override
    public List<RolePermissionGroup> listRolePermissionGroupByRoleIds(List<Integer> roleIds) {
        return rolePermissionGroupMapper.listRolePermissionGroupByRoleIds(roleIds);
    }
}
