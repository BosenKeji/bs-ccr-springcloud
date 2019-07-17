package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Strategy;

import java.util.List;

public interface StrategyMapper {
    int insert(Strategy record);

    int insertSelective(Strategy record);

    Strategy findStrategy(Integer id);

    List<Strategy> findAll();
}