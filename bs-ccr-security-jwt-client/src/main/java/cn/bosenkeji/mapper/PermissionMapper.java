package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.Permission;

import java.util.List;


public interface PermissionMapper {
    int insert(Permission permission);

    int insertSelective(Permission permission);

    int savePermissionBatch(List<Permission> list);

    int updateBySelective(Permission permission);

    int update(Permission permission);

    Permission getPermissionById(Integer id);

    List<Permission> listPermission();

    List<Permission> listPermissionByIds(List<Integer> list);
}