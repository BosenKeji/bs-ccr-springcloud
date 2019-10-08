package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.PermissionGroupPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionGroupPermissionMapper {
    int insert(PermissionGroupPermission permissionGroupPermission);

    int insertSelective(PermissionGroupPermission permissionGroupPermission);

    List<Integer> savePermissionGroupPermissionBatch(@Param("groupId") Integer groupId, @Param("list") List<Integer> ids);

    int updateBySelective(PermissionGroupPermission permissionGroupPermission);

    int update(PermissionGroupPermission permissionGroupPermission);

    PermissionGroupPermission getPermissionGroupPermissionById(Integer id);

    List<PermissionGroupPermission> listPermissionGroupPermission();

    List<PermissionGroupPermission> listPermissionGroupPermissionByGroupId(Integer groupId);
}