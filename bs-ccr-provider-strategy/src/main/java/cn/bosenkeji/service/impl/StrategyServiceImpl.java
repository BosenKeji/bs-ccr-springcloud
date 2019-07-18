package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategyAttributeMapper;
import cn.bosenkeji.mapper.StrategyMapper;
import cn.bosenkeji.mapper.StrategySequenceMapper;
import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.LastOwnerException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService{


    @Autowired
    protected StrategyMapper strategyMapper;

    @Autowired
    private StrategyAttributeMapper strategyAttributeMapper;

    @Autowired
    private StrategySequenceMapper strategySequenceMapper;


    @Override
    public boolean addStrategyAttributeBySelective(Strategy strategy) {
        strategy.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        strategy.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        int result = strategyMapper.insertSelective(strategy);
        boolean b = false;
        if (result > 0) {
            b = true;
        }
        return b;
    }

    @Override
    public boolean insertStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
        boolean b = false;
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
            int result = strategyAttributeMapper.insertSelective(strategyAttribute);
            if (result > 0) {
                b = true;
            }
        }
        return b;
    }

    @Override
    public StrategyVO getStrategy(Integer id) {
        Strategy strategy = strategyMapper.findStrategy(id);
        StrategyAttribute strategyAttribute = strategyAttributeMapper.findStrategyAttributeByStrategyId(id);
        StrategyVO strategyVO = convertStrategyVO(strategy, strategyAttribute);
        return strategyVO;
    }

    @Override
    public PageInfo<Strategy> listByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(strategyMapper.findAll());
    }


    private StrategyVO convertStrategyVO(Strategy strategy,StrategyAttribute strategyAttribute) {
        StrategyVO strategyVO = new StrategyVO();
        strategyVO.setId(strategy.getId());
        strategyVO.setName(strategy.getName());
        strategyVO.setLever(strategyAttribute.getLever());
        strategyVO.setRate(strategyAttribute.getRate());
        strategyVO.setBuildReference(strategyAttribute.getBuildReference());
        strategyVO.setStatus(strategy.getStatus());
        return strategyVO;
    }
}
