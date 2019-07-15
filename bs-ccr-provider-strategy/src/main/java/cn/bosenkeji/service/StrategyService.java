package cn.bosenkeji.service;

import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;

import java.util.List;

public interface StrategyService {

    //获取指定id的策略
    Strategy getStrategy(Integer id);

    //获取所有的策略（简单）
    List<Strategy> getStrategies();

    //获取指定策略的属性
    StrategyAttribute getStrategyAttribute(Integer strategyId);

    //获取指定策略的杠杆倍数
    Integer getLeverByStrategyId(Integer strategyId);
}
