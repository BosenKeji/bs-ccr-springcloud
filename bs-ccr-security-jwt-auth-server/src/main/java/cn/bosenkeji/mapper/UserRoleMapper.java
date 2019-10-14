package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.permission.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole userRole);

    int insertSelective(UserRole userRole);

    int updateBySelective(UserRole userRole);

    int update(UserRole userRole);

    UserRole getUserRoleById(Integer id);

    List<UserRole> listUserRole();

    List<UserRole> listUserRoleByUserIdAndType(@Param("userId") Integer userId, @Param("type") Integer type);
}