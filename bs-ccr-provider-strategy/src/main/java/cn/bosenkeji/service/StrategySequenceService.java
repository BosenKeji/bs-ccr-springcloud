package cn.bosenkeji.service;


import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

public interface StrategySequenceService {

    Optional<Integer> insertStrategySequenceBySelective(StrategySequence sequence);

    Optional<Integer> insertStrategySequenceValueBySelective(StrategySequenceValue sequenceValue);

    PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize);

    StrategySequenceVO findSequenceByPrimaryKey(Integer id);

    String getSequenceValueByStrategyId(Integer strategyId);
}
