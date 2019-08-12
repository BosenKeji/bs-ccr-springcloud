package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategyAttributeMapper;
import cn.bosenkeji.mapper.StrategyMapper;
import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StrategyServiceImpl implements StrategyService{


    @Autowired
    protected StrategyMapper strategyMapper;

    @Autowired
    private StrategyAttributeMapper strategyAttributeMapper;


    @Override
    public Optional<Integer> addStrategyBySelective(Strategy strategy) {
        strategy.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        strategy.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(strategyMapper.insertSelective(strategy));
    }

    @Override
    public Optional<Integer> insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
        strategyAttribute.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        strategyAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(strategyAttributeMapper.insertSelective(strategyAttribute));
    }

    @Override
    public StrategyOther getStrategy(Integer id) {
        Strategy strategy = strategyMapper.findStrategy(id);
        if (strategy == null) {
            return null;
        }
        StrategyAttribute strategyAttribute = strategyAttributeMapper.findStrategyAttributeByStrategyId(id);
        return convertStrategyOther(strategy, strategyAttribute);
    }

    @Override
    public PageInfo<Strategy> listByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(strategyMapper.findAll());
    }

    @Override
    public Optional<Integer> checkStrategyByName(String name) {
        return Optional.of(strategyMapper.checkStrategyExistByName(name));
    }


    @Override
    public Optional<Integer> checkStrategyById(Integer id) {
        return Optional.of(strategyMapper.checkStrategyExistById(id));
    }

    @Override
    public Optional<Integer> checkAttributeByIdOrNameOrStrategyId(Integer id, String name, Integer strategyId) {
        return Optional.of(strategyAttributeMapper.checkStrategyAttributeByIdOrNameOrStrategyId(id,name,strategyId));
    }

    @Override
    public Optional<Integer> checkStrategyAttributeBySequenceId(Integer sequenceId) {
        return Optional.of(strategyAttributeMapper.checkStrategyAttributeBySequenceId(sequenceId));
    }

    private StrategyOther convertStrategyOther(Strategy strategy, StrategyAttribute strategyAttribute) {
        StrategyOther other = new StrategyOther();
        other.setId(strategy.getId());
        other.setName(strategy.getName());
        other.setStatus(strategy.getStatus());

        other.setIsDefault(strategyAttribute.getIsDefault());
        other.setIsTip(strategyAttribute.getIsTip());
        other.setLever(strategyAttribute.getLever());

        other.setStrategySequenceId(strategyAttribute.getStrategySequenceId());
        other.setRate(strategyAttribute.getRate());
        other.setStopProfitRatio(strategyAttribute.getStopProfitRatio()/100);

        other.setIsStopProfitTrace(strategyAttribute.getIsStopProfitTrace());
        other.setStopProfitTraceTriggerRate(strategyAttribute.getStopProfitTraceTriggerRate()/100);
        other.setStopProfitTraceDropRate(strategyAttribute.getStopProfitTraceDropRate()/100);

        other.setIsStopProfitMoney(strategyAttribute.getIsStopProfitMoney());
        other.setIsStopProfitGrid(strategyAttribute.getIsStopProfitGrid());
        other.setBuildReference(strategyAttribute.getBuildReference());
        return other;
    }
}
