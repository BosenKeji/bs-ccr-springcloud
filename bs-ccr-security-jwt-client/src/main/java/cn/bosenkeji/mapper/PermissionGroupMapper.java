package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.PermissionGroup;

import java.util.List;

public interface PermissionGroupMapper {
    int insert(PermissionGroup permissionGroup);

    int insertSelective(PermissionGroup permissionGroup);

    int updateBySelective(PermissionGroup permissionGroup);

    int update(PermissionGroup permissionGroup);

    PermissionGroup getPermissionGroupById(Integer id);

    List<PermissionGroup> listPermissionGroup();
}