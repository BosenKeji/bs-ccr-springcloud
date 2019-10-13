package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface UserProductComboMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserProductCombo record);

    int insertSelective(UserProductCombo record);

    UserProductCombo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserProductCombo record);

    int updateByPrimaryKey(UserProductCombo record);

    int updateRedisKey(UserProductCombo record);

    List<UserProductCombo> findAll();
    List<Integer> selectPrimaryKeyByUserId(int userId);


    //List<UserProductCombo> selectUserProductComboByUserTel(String userTel);

    List<UserProductCombo> selectUserProductComboByUserId(Integer userId);

    Integer checkExistByProductIdAndUserId(Integer productId, Integer userId);

    @MapKey("id")
    Map<Integer,UserProductCombo> selectByPrimaryKeys(List<Integer> ids);

}