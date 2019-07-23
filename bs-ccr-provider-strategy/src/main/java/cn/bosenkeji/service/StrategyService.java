package cn.bosenkeji.service;


import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;

import java.util.Optional;


public interface StrategyService {

    Optional<Integer> addStrategyAttributeBySelective(Strategy strategy);

    Optional<Integer> insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

    StrategyVO getStrategy(Integer id);

    PageInfo<Strategy>  listByPage(Integer pageNum, Integer pageSize);


}
