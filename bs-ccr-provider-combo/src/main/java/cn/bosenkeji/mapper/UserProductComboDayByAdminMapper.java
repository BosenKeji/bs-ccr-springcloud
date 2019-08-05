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

    /**
     * 单表查询
     * @return
     */
    List<UserProductComboDayByAdmin> findAll();

    /**
     * 多表user amdin userproductcomboday userproductcombodaybyadmin联合查询
     * @return
     */
    List<UserProductComboDayByAdmin> selectUserProductComboDayByAdminList();

    List<UserProductComboDayByAdmin> selectUserProductComboDayByUserProductComboId(int userProductComboId);
    List<UserProductComboDayByAdmin> selectUserProductComboDayByUserTel(String  userTel);

    UserProductComboDayByAdmin selectByUserProductComboDayId(int userProductComboDayId);

}