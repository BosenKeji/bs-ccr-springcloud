package cn.bosenkeji.service;

import cn.bosenkeji.vo.permission.Role;
import cn.bosenkeji.vo.permission.RolePermissionGroup;
import cn.bosenkeji.vo.permission.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Optional<Integer> saveRole(Role role);

    List<Role> listRole(Integer pageNum, Integer pageSize);

    Optional<Role> getRoleById(Integer id);

    Optional<Integer> updateRoleById(Role role);

    Optional<Integer> deleteRoleById(Integer id);

    Optional<Integer> saveUserRole(UserRole userRole);

    Optional<Integer> updateUserRole(UserRole userRole);

    Optional<Integer> deleteUserRole(Integer id);

    Optional<UserRole> getUserRole(Integer id);

    List<UserRole> listUserRole();

    List<UserRole> listUserRoleByUserIdAndType(Integer userId, Integer type);

    List<RolePermissionGroup> listRolePermissionGroupByRoleIds(List<Integer> roleIds);

}
