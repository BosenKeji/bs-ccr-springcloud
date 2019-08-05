package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;

import java.util.List;

public interface UserProductComboDayByAdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserProductComboDayByAdmin record);

    int insertSelective(UserProductComboDayByAdmin record);

    UserProductComboDayByAdmin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserProductComboDayByAdmin record);

    int updateByPrimaryKey(UserProductComboDayByAdmin record);

    UserProductComboDayByAdmin selectByUserProductComboDayId(int userProductComboDayId);

    /**
     * 单表查询
     * @return
     */
    List<UserProductComboDayByAdmin> findAll();



}