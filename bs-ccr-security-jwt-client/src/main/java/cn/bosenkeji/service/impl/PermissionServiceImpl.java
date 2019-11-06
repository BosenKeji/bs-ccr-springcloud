package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.PermissionMapper;
import cn.bosenkeji.service.PermissionService;
import cn.bosenkeji.vo.permission.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {


    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public Optional<Integer> savePermission(Permission permission) {
        permission.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        permission.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = permissionMapper.insertSelective(permission);
        return Optional.of(result);
    }

    @Override
    public List<Integer> savePermissionBatch(List<Permission> permissionList) {
        int i = permissionMapper.savePermissionBatch(permissionList);
        List<Integer> ids = new ArrayList<>();
        if (i > 0) {
            ids = permissionList.stream().map(Permission::getId).collect(Collectors.toList());
        }
        return ids;
    }

    @Override
    public List<Permission> listPermission(Integer pageNum, Integer pageSize) {
        return permissionMapper.listPermission();
    }

    @Override
    public List<Permission> listPermissionByIds(List<Integer> ids) {
        return permissionMapper.listPermissionByIds(ids);
    }

    @Override
    public Permission getPermissionById(Integer id) {
        return permissionMapper.getPermissionById(id);
    }

    @Override
    public Optional<Integer> updatePermissionById(Permission permission) {
        permission.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = permissionMapper.updateBySelective(permission);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deletePermissionById(Integer id) {
        Permission permission = new Permission();
        permission.setId(id);
        permission.setStatus(0);
        int result = permissionMapper.updateBySelective(permission);
        return Optional.of(result);
    }




}
