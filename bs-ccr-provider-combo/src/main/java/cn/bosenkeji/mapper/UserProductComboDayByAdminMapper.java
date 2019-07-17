package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductComboDayByAdmin;

import java.util.List;

public interface UserProductComboDayByAdminMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(UserProductComboDayByAdmin record);

    int insertSelective(UserProductComboDayByAdmin record);

    UserProductComboDayByAdmin selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(UserProductComboDayByAdmin record);

    int updateByPrimaryKey(UserProductComboDayByAdmin record);

    List<UserProductComboDayByAdmin> findAll();

    List<UserProductComboDayByAdmin> selectUserProductComboDayByUserProductComboId(int userProductComboId);
}