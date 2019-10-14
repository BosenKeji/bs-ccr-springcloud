package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.PermissionGroupMapper;
import cn.bosenkeji.mapper.PermissionGroupPermissionMapper;
import cn.bosenkeji.service.PermissionGroupService;
import cn.bosenkeji.service.PermissionService;
import cn.bosenkeji.vo.permission.Permission;
import cn.bosenkeji.vo.permission.PermissionGroup;
import cn.bosenkeji.vo.permission.PermissionGroupOther;
import cn.bosenkeji.vo.permission.PermissionGroupPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionGroupServiceImpl implements PermissionGroupService {

    @Autowired
    private PermissionGroupMapper permissionGroupMapper;

    @Autowired
    private PermissionGroupPermissionMapper permissionGroupPermissionMapper;

    @Autowired
    PermissionService permissionService;

    @Override
    public Optional<Integer> savePermissionGroup(PermissionGroup permissionGroup) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        permissionGroup.setCreatedAt(timestamp);
        permissionGroup.setUpdatedAt(timestamp);
        int result = permissionGroupMapper.insertSelective(permissionGroup);
        return Optional.of(result);
    }

    @Override
    public List<PermissionGroup> listPermissionGroup() {
        return permissionGroupMapper.listPermissionGroup();
    }

    @Override
    public PermissionGroup getPermissionGroupById(Integer id) {
        return permissionGroupMapper.getPermissionGroupById(id);
    }

    @Override
    public Optional<Integer> updatePermissionGroupById(PermissionGroup permissionGroup) {
        permissionGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        int result = permissionGroupMapper.updateBySelective(permissionGroup);
        return Optional.of(result);
    }

    @Override
    public Optional<Integer> deletePermissionGroupById(Integer id) {
        PermissionGroup permissionGroup = new PermissionGroup();
        permissionGroup.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        permissionGroup.setStatus(0);
        permissionGroup.setId(id);
        int result = permissionGroupMapper.updateBySelective(permissionGroup);
        return Optional.of(result);
    }


    @Override
    public PermissionGroupOther getPermissionGroupJoin(Integer id) {
        PermissionGroup permissionGroup = getPermissionGroupById(id);
        if (permissionGroup != null) {
            List<PermissionGroupPermission> permissionGroupPermissionList = permissionGroupPermissionMapper.listPermissionGroupPermissionByGroupId(id);
            List<Integer> permissionIds = permissionGroupPermissionList.stream().map(PermissionGroupPermission::getPermissionId).collect(Collectors.toList());

            List<Permission> permissionList = permissionService.listPermissionByIds(permissionIds);
            //过滤status为0的Permission
            List<Permission> filterPermissionList = permissionList.stream().filter((p) -> p.getStatus() == 1).collect(Collectors.toList());

            return converterPermissionOther(permissionGroup,filterPermissionList);
        }
        return new PermissionGroupOther();
    }

    @Override
    public List<PermissionGroupOther> listPermissionGroupJoin() {
        List<PermissionGroup> permissionGroupList = listPermissionGroup();
        List<PermissionGroupOther> otherList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(permissionGroupList)) {

            permissionGroupList.stream().filter((p) -> p.getStatus() == 1)
                                        .forEach((p) ->{
                                            PermissionGroupOther other = getPermissionGroupJoin(p.getId());
                                            otherList.add(other);
                                        });
        }
        return otherList;
    }

    @Override
    @Transactional
    public Optional<Integer> savePermissionGroupJoin(Integer groupId, List<Permission> permissionList) {
        PermissionGroup group = getPermissionGroupById(groupId);
        List result = new ArrayList();
        if (group !=null) {
            List<Integer> permissionIds = permissionService.savePermissionBatch(permissionList); //返回插入成功的ids
            result = permissionGroupPermissionMapper.savePermissionGroupPermissionBatch(groupId, permissionIds);
        }
        return Optional.of(result.size());
    }

    private PermissionGroupOther converterPermissionOther(PermissionGroup group,List<Permission> permissionList) {
        PermissionGroupOther other = new PermissionGroupOther();
        other.setGroupId(group.getId());
        other.setName(group.getName());
        other.setDescription(group.getDescription());
        other.setPermissionList(permissionList);
        return other;
    }


    public List<PermissionGroup> listPermissionGroupByIds(List<Integer> ids) {
        return permissionGroupMapper.listPermissionGroupByIds(ids);
    }

}
