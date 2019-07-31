package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategyAttributeMapper;
import cn.bosenkeji.mapper.StrategyMapper;
import cn.bosenkeji.mapper.StrategySequenceMapper;
import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategySequence;
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

    @Autowired
    private StrategySequenceMapper strategySequenceMapper;


    @Override
    public Optional<Integer> addStrategyBySelective(Strategy strategy) {
        strategy.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        strategy.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(strategyMapper.insertSelective(strategy));
    }

    @Override
    public Optional<Integer> insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
        Optional result = Optional.ofNullable(null);
        boolean checkStrategy = false , checkSequence = false;
        Strategy strategy = strategyMapper.findStrategy(strategyAttribute.getStrategyId());
        StrategySequence sequence = strategySequenceMapper.findSequenceByPrimaryKey(strategyAttribute.getStrategySequenceId());
        if (strategy != null) {
            checkStrategy = true;
        }
        if (sequence != null) {
            checkSequence = true;
        }
        if (checkStrategy && checkSequence) {
            strategyAttribute.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            strategyAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            int i = strategyAttributeMapper.insertSelective(strategyAttribute);
            if (i > 0) {
                result = Optional.of(i);
            }
        }
        return result;
    }

    @Override
    public StrategyOther getStrategy(Integer id) {
        Strategy strategy = strategyMapper.findStrategy(id);
        StrategyAttribute strategyAttribute = strategyAttributeMapper.findStrategyAttributeByStrategyId(id);
        StrategyOther strategyOther = convertStrategyOther(strategy, strategyAttribute);
        return strategyOther;
    }

    @Override
    public PageInfo<Strategy> listByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(strategyMapper.findAll());
    }

    @Override
    public Optional<Integer> checkStrategyByName(String name) {
        return Optional.ofNullable(strategyMapper.checkStrategyExistByName(name));
    }


    @Override
    public Optional<Integer> checkStrategyById(Integer id) {
        return Optional.of(strategyMapper.checkStrategyExistById(id));
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
        other.setStopProfitRatio(strategyAttribute.getStopProfitRatio());

        other.setIsStopProfitTrace(strategyAttribute.getIsStopProfitTrace());
        other.setStopProfitTraceTriggerRate(strategyAttribute.getStopProfitTraceTriggerRate());
        other.setStopProfitTraceDropRate(strategyAttribute.getStopProfitTraceDropRate());

        other.setIsStopProfitMoney(strategyAttribute.getIsStopProfitMoney());
        other.setIsStopProfitGrid(strategyAttribute.getIsStopProfitGrid());
        other.setBuildReference(strategyAttribute.getBuildReference());
        return other;
    }
}
