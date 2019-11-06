package cn.bosenkeji.mapper;


import cn.bosenkeji.vo.reason.Reason;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ReasonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reason record);

    int insertSelective(Reason record);

    Reason selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reason record);

    int updateByPrimaryKey(Reason record);

    List<Reason> findAll();
    List<Reason> selectByReasonType(Integer reasonTypeId);

    @MapKey("id")
    Map<Integer,Reason> selectByPrimaryKeys(Set<Integer> set);

    int checkExistById(Integer id);

}