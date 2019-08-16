package cn.bosenkeji.service;


import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceOther;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

public interface StrategySequenceService {

    Optional<Integer> insertStrategySequenceBySelective(StrategySequence sequence);

    Optional<Integer> insertStrategySequenceValueBySelective(StrategySequenceValue sequenceValue);

    PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize);

    StrategySequenceOther findSequenceByPrimaryKey(Integer id);

    String getSequenceValueByStrategyId(Integer strategyId);

    Optional<Integer> checkSequenceByName(String name);

    Optional<Integer> checkSequenceById(Integer id);

    Optional<Integer> checkSequenceByIdOrValueOrSequenceId(Integer id,String value,Integer sequenceId);
}
