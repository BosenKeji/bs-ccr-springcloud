package cn.bosenkeji.service;


import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;

import java.util.Optional;


public interface StrategyService {

    Optional<Integer> addStrategyBySelective(Strategy strategy);

    Optional<Integer> insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

    StrategyOther getStrategy(Integer id);

    PageInfo<Strategy>  listByPage(Integer pageNum, Integer pageSize);

    Optional<Integer> checkStrategyByName(String name);

    Optional<Integer> checkStrategyById(Integer id);

    Optional<Integer> checkStrategyAttributeBySequenceId(Integer sequenceId);

    Optional<Integer> checkAttributeByIdOrNameOrStrategyId(Integer id, String name, Integer strategyId);
}
