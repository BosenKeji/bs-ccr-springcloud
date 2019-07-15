package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategySequenceMapper;
import cn.bosenkeji.mapper.StrategySequenceValueMapper;
import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategySequenceServiceImpl implements StrategySequenceService {

    @Autowired
    private StrategySequenceMapper strategySequenceMapper;

    @Autowired
    private StrategySequenceValueMapper strategySequenceValueMapper;


    @Override
    public StrategySequence getStrategySequence(Integer id) {
        return strategySequenceMapper.getStrategySequenceByPrimaryKey(id);
    }

    @Override
    public StrategySequenceValue getSequenceValue(Integer sequenceId) {
        return strategySequenceValueMapper.getSequenceValueBySequenceId(sequenceId);
    }

    @Override
    public List<StrategySequence> getSequences() {
        return strategySequenceMapper.getSequences();
    }


}
