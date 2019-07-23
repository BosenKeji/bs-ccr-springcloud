package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.strategy.StrategySequence;

import java.util.List;

public interface StrategySequenceMapper {
    int insert(StrategySequence record);

    int insertSelective(StrategySequence record);

    List<StrategySequence> findAll();

    StrategySequence findSequenceByPrimaryKey(Integer id);
}