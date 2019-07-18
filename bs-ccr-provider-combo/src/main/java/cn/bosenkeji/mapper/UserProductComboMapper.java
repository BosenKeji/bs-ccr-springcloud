package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductCombo;

import java.util.List;

public interface UserProductComboMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(UserProductCombo record);

    int insertSelective(UserProductCombo record);

    UserProductCombo selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(UserProductCombo record);

    boolean updateByPrimaryKey(UserProductCombo record);

    List<UserProductCombo> findAll();
    List<Integer> selectPrimaryKeyByUserId(int userId);


    List<UserProductCombo> selectUserProductComboByUserTel(String userTel);

}