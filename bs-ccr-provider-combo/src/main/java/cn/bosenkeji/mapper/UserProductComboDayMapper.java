package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductComboDay;

import java.util.List;

public interface UserProductComboDayMapper {
    int deleteByPrimaryKey(Integer id);

    boolean insert(UserProductComboDay record);

    int insertSelective(UserProductComboDay record);

    UserProductComboDay selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(UserProductComboDay record);

    int updateByPrimaryKey(UserProductComboDay record);

    List<UserProductComboDay> findAll();
}