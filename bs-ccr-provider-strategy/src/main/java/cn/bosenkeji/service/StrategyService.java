package cn.bosenkeji.service;


import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;

import java.util.Optional;


public interface StrategyService {

    Optional<Integer> addStrategyAttributeBySelective(Strategy strategy);

    Optional<Integer> insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

    StrategyOther getStrategy(Integer id);

    PageInfo<Strategy>  listByPage(Integer pageNum, Integer pageSize);


}
