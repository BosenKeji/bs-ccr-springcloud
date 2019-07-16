package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Strategy;

import java.util.List;


public interface StrategyMapper {

    Strategy getStrategyByprimaryKey(Integer id);

    List<Strategy> getStrategies();

}