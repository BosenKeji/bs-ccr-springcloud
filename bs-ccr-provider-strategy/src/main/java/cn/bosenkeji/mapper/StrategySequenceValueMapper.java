package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.StrategySequenceValue;


public interface StrategySequenceValueMapper {


    StrategySequenceValue getSequenceValueByPrimaryKey(Integer id);

    StrategySequenceValue getSequenceValueBySequenceId(Integer sequenceId);


}