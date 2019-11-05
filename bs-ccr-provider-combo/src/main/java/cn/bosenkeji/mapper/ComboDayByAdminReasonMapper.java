package cn.bosenkeji.mapper;


import cn.bosenkeji.vo.combo.ComboDayByAdminReason;
import org.apache.ibatis.annotations.MapKey;
import org.omg.CORBA.INTERNAL;

import java.util.List;
import java.util.Map;

public interface ComboDayByAdminReasonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ComboDayByAdminReason record);

    int insertSelective(ComboDayByAdminReason record);

    ComboDayByAdminReason selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ComboDayByAdminReason record);

    int updateByPrimaryKey(ComboDayByAdminReason record);

    List<ComboDayByAdminReason> findAll();

    @MapKey("id")
    Map<Integer,ComboDayByAdminReason> listByComboDayByAdminIds(List<Integer> ids);
}