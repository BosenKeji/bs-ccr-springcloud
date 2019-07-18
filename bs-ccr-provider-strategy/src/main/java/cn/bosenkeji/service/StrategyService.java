package cn.bosenkeji.service;


import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;


public interface StrategyService {

    boolean addStrategyAttributeBySelective(Strategy strategy);

    boolean insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

    StrategyVO getStrategy(Integer id);

    PageInfo<Strategy>  listByPage(Integer pageNum, Integer pageSize);


}
