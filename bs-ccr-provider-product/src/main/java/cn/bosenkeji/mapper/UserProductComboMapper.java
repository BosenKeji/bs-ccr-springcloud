package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductCombo;
import cn.bosenkeji.vo.UserProductComboExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProductComboMapper {
    long countByExample(UserProductComboExample example);

    int deleteByExample(UserProductComboExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserProductCombo record);

    int insertSelective(UserProductCombo record);

    List<UserProductCombo> selectByExample(UserProductComboExample example);

    UserProductCombo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserProductCombo record, @Param("example") UserProductComboExample example);

    int updateByExample(@Param("record") UserProductCombo record, @Param("example") UserProductComboExample example);

    int updateByPrimaryKeySelective(UserProductCombo record);

    int updateByPrimaryKey(UserProductCombo record);
}