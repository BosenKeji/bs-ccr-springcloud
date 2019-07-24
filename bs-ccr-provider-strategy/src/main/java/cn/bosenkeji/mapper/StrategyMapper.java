package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.strategy.Strategy;

import java.util.List;

public interface StrategyMapper {
    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy findStrategy(Integer id);

    List<Strategy> findAll();

    int checkStrategyExistByName(String name);

    int checkStrategyExistById(Integer id);
}