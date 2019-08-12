package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import org.apache.ibatis.annotations.Param;

public interface StrategySequenceValueMapper {
    int insert(StrategySequenceValue record);

    int insertSelective(StrategySequenceValue record);

    StrategySequenceValue findSequenceValueBySequenceId(Integer sequenceId);

    String getSequenceValueByStrategyId(Integer strategyId);

    int checkSequenceByIdOrValueOrSequenceId(@Param("id") Integer id, @Param("value") String value, @Param("sequenceId") Integer sequenceId);
}