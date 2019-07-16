package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategySequenceMapper;
import cn.bosenkeji.mapper.StrategySequenceValueMapper;
import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StrategySequenceServiceImpl implements StrategySequenceService {

    @Autowired
    private StrategySequenceMapper strategySequenceMapper;

    @Autowired
    private StrategySequenceValueMapper strategySequenceValueMapper;


    @Override
    public PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(strategySequenceMapper.findAll());
    }

    @Override
    public StrategySequenceVO findSequenceByPrimaryKey(Integer id) {
        StrategySequence sequence = strategySequenceMapper.findSequenceByPrimaryKey(id);
        StrategySequenceValue sequenceValue = strategySequenceValueMapper.findSequenceValueBySequenceId(id);
        StrategySequenceVO sequenceVO = convertStrategySequenceVo(sequence,sequenceValue);
        return sequenceVO;
    }

    @Override
    public String getSequenceValueByStrategyId(Integer strategyId) {
        return strategySequenceValueMapper.getSequenceValueByStrategyId(strategyId);
    }

    private StrategySequenceVO convertStrategySequenceVo(StrategySequence sequence, StrategySequenceValue sequenceValue) {
        StrategySequenceVO sequenceVO = new StrategySequenceVO();
        sequenceVO.setId(sequence.getId());
        sequenceVO.setName(sequence.getName());
        sequenceVO.getSortNum(sequenceValue.getSortNum());
        sequenceVO.setTip(sequence.getTip());
        sequenceVO.setValue(sequenceValue.getValue());
        sequenceVO.setStatus(sequence.getStatus());
        return sequenceVO;
    }

}
