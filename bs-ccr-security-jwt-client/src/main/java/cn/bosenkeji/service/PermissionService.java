package cn.bosenkeji.service;

import cn.bosenkeji.vo.permission.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionService {

    Optional<Integer> savePermission(Permission permission);

    List<Integer> savePermissionBatch(List<Permission> permissionList);

    Permission getPermissionById(Integer id);

    List<Permission> listPermission(Integer pageNum, Integer pageSize);

    Optional<Integer> updatePermissionById(Permission permission);

    Optional<Integer> deletePermissionById(Integer id);

    List<Permission> listPermissionByIds(List<Integer> ids);
}
