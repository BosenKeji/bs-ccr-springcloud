package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import cn.bosenkeji.vo.UserProductComboDayByAdminExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserProductComboDayByAdminMapper {
    long countByExample(UserProductComboDayByAdminExample example);

    int deleteByExample(UserProductComboDayByAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserProductComboDayByAdmin record);

    int insertSelective(UserProductComboDayByAdmin record);

    List<UserProductComboDayByAdmin> selectByExample(UserProductComboDayByAdminExample example);

    UserProductComboDayByAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserProductComboDayByAdmin record, @Param("example") UserProductComboDayByAdminExample example);

    int updateByExample(@Param("record") UserProductComboDayByAdmin record, @Param("example") UserProductComboDayByAdminExample example);

    int updateByPrimaryKeySelective(UserProductComboDayByAdmin record);

    int updateByPrimaryKey(UserProductComboDayByAdmin record);
}