package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.StrategyAttribute;

public interface StrategyAttributeMapper {
    int insert(StrategyAttribute record);

    int insertSelective(StrategyAttribute record);

    StrategyAttribute findStrategyAttributeByStrategyId(Integer strategyId);
}