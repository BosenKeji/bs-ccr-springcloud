package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.RolePermissionGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionGroupMapper {
    int insert(RolePermissionGroup rolePermissionGroup);

    int insertSelective(RolePermissionGroup rolePermissionGroup);

    int updateBySelective(RolePermissionGroup rolePermissionGroup);

    int update(RolePermissionGroup rolePermissionGroup);

    RolePermissionGroup getRolePermissionGroupById(Integer id);

    List<RolePermissionGroup> listRolePermissionGroup();

    List<RolePermissionGroup> listRolePermissionGroupByRoleIds(@Param("roleIds") List<Integer> roleIds);
}