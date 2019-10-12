package cn.bosenkeji.service;

import cn.bosenkeji.vo.permission.Permission;
import cn.bosenkeji.vo.permission.PermissionGroup;
import cn.bosenkeji.vo.permission.PermissionGroupOther;

import java.util.List;
import java.util.Optional;

public interface PermissionGroupService {

    Optional<Integer> savePermissionGroup(PermissionGroup permissionGroup);

    List<PermissionGroup> listPermissionGroup();

    PermissionGroup getPermissionGroupById(Integer id);

    Optional<Integer> updatePermissionGroupById(PermissionGroup permissionGroup);

    Optional<Integer> deletePermissionGroupById(Integer id);

    PermissionGroupOther getPermissionGroupJoin(Integer id);

    List<PermissionGroupOther> listPermissionGroupJoin();

    Optional<Integer> savePermissionGroupJoin(Integer groupId, List<Permission> permissionList);

    List<PermissionGroup> listPermissionGroupByIds(List<Integer> ids);
}
