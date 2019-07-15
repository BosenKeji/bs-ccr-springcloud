package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.StrategySequence;

import java.util.List;


public interface StrategySequenceMapper {


    StrategySequence getStrategySequenceByPrimaryKey(Integer id);

    List<StrategySequence> getSequences();
}