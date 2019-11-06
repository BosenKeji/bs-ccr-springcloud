package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.Role;

import java.util.List;

public interface RoleMapper {
    int insert(Role role);

    int insertSelective(Role role);

    int updateBySelective(Role role);

    int update(Role role);

    Role getRoleById(Integer id);

    List<Role> listRole();
}