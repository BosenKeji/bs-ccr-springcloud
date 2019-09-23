package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.ComboDayReason;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface ComboDayReasonMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ComboDayReason record);

    int insertSelective(ComboDayReason record);

    ComboDayReason selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(ComboDayReason record);

    int updateByPrimaryKey(ComboDayReason record);

    List<ComboDayReason> findAll();

    @MapKey("id")
    Map<Integer,ComboDayReason> selectByPrimaryKeys(List<Integer> ids);
}