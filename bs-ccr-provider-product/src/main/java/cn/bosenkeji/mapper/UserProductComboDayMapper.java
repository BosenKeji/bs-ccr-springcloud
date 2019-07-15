package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProductComboDayMapper {
    long countByExample(UserProductComboDayExample example);

    int deleteByExample(UserProductComboDayExample example);

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(UserProductComboDay record);

    int insertSelective(UserProductComboDay record);

    List<UserProductComboDay> selectByExample(UserProductComboDayExample example);

    UserProductComboDay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserProductComboDay record, @Param("example") UserProductComboDayExample example);

    int updateByExample(@Param("record") UserProductComboDay record, @Param("example") UserProductComboDayExample example);

    boolean updateByPrimaryKeySelective(UserProductComboDay record);

    int updateByPrimaryKey(UserProductComboDay record);
}