package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.StrategyAttribute;


public interface StrategyAttributeMapper {

    StrategyAttribute getStrategyAttributeByPrimaryKey(Integer id);

    StrategyAttribute getStrategyAttributeByStrategyId(Integer strategyId);

    Integer getLevelByStrategyId(Integer strategyId);


}