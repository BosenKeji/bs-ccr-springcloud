package cn.bosenkeji.mapper;


import cn.bosenkeji.vo.reason.ReasonType;

import java.util.List;

public interface ReasonTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ReasonType record);

    int insertSelective(ReasonType record);

    ReasonType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReasonType record);

    int updateByPrimaryKey(ReasonType record);

    List<ReasonType> findAll();
}