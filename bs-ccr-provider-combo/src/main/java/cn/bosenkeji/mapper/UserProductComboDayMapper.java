package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.UserProductComboDay;

import java.util.List;

public interface UserProductComboDayMapper {
    int deleteByPrimaryKey(Integer id);

    boolean insert(UserProductComboDay record);

    int insertSelective(UserProductComboDay record);

    boolean updateByPrimaryKeySelective(UserProductComboDay record);

    UserProductComboDay selectByPrimaryKey(Integer id);

    List<UserProductComboDay> selectByUserId(Integer userId);

    List<UserProductComboDay> selectByUserProductComboId(Integer userProductComboId);

    int updateByPrimaryKey(UserProductComboDay record);

    List<UserProductComboDay> findAll();
}