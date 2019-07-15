package cn.bosenkeji.service;

import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceValue;

import java.util.List;

public interface StrategySequenceService {

    StrategySequence getStrategySequence(Integer id);

    StrategySequenceValue getSequenceValue(Integer sequenceId);

    List<StrategySequence> getSequences();
}
