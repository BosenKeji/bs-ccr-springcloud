package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategyAttributeMapper;
import cn.bosenkeji.mapper.StrategyMapper;
import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private StrategyAttributeMapper strategyAttributeMapper;

    @Override
    public Strategy getStrategy(Integer id) {
        return strategyMapper.getStrategyByprimaryKey(id);
    }

    @Override
    public List<Strategy> getStrategies() {
        return strategyMapper.getStrategies();
    }

    @Override
    public StrategyAttribute getStrategyAttribute(Integer strategyId) {
        return strategyAttributeMapper.getStrategyAttributeByStrategyId(strategyId);
    }

    @Override
    public Integer getLeverByStrategyId(Integer strategyId) {
        return strategyAttributeMapper.getLevelByStrategyId(strategyId);
    }

}
