package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategyAttributeMapper;
import cn.bosenkeji.mapper.StrategyMapper;
import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService{


    @Autowired
    protected StrategyMapper strategyMapper;

    @Autowired
    private StrategyAttributeMapper strategyAttributeMapper;


    @Override
    public StrategyVO getStrategy(Integer id) {
        Strategy strategy = strategyMapper.findStrategy(id);
        StrategyAttribute strategyAttribute = strategyAttributeMapper.findStrategyAttributeByStrategyId(id);
        StrategyVO strategyVO = convertStrategyVO(strategy, strategyAttribute);
        return strategyVO;
    }

    @Override
    public PageInfo<Strategy> listBypage(Integer pageNum, Integer pageSize) {
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
