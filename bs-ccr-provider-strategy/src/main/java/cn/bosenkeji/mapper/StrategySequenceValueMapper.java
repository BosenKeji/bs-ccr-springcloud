package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.StrategySequenceValue;

public interface StrategySequenceValueMapper {
    int insert(StrategySequenceValue record);

    int insertSelective(StrategySequenceValue record);

    StrategySequenceValue findSequenceValueBySequenceId(Integer sequenceId);

    String getSequenceValueByStrategyId(Integer strategyId);
}