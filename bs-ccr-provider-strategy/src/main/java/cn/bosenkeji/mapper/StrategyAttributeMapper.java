package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.strategy.StrategyAttribute;
import org.apache.ibatis.annotations.Param;

public interface StrategyAttributeMapper {
    int insert(StrategyAttribute record);

    int insertSelective(StrategyAttribute record);

    StrategyAttribute findStrategyAttributeByStrategyId(Integer strategyId);

    int checkStrategyAttributeBySequenceId(Integer sequenceId);

    int checkStrategyAttributeByIdOrNameOrStrategyId(@Param("id") Integer id, @Param("name") String name, @Param("strategyId") Integer strategyId);
}