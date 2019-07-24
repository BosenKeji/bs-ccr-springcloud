package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.StrategySequenceMapper;
import cn.bosenkeji.mapper.StrategySequenceValueMapper;
import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceOther;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class StrategySequenceServiceImpl implements StrategySequenceService {

    @Autowired
    private StrategySequenceMapper strategySequenceMapper;

    @Autowired
    private StrategySequenceValueMapper strategySequenceValueMapper;


    @Override
    public Optional<Integer> insertStrategySequenceBySelective(StrategySequence sequence) {
        sequence.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        sequence.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(strategySequenceMapper.insertSelective(sequence));
    }

    @Override
    public Optional<Integer> insertStrategySequenceValueBySelective(StrategySequenceValue sequenceValue) {
        Optional<Integer> o = Optional.of(0);
        StrategySequence checkSequence = strategySequenceMapper.findSequenceByPrimaryKey(sequenceValue.getStrategySequenceId());
        sequenceValue.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        sequenceValue.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        if ( checkSequence != null ) {
            int i = strategySequenceValueMapper.insertSelective(sequenceValue);
            if ( i > 0 ) {
                o = Optional.of(i);
            }
        }
        return o;
    }

    @Override
    public PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(strategySequenceMapper.findAll());
    }

    @Override
    public StrategySequenceOther findSequenceByPrimaryKey(Integer id) {
        StrategySequence sequence = strategySequenceMapper.findSequenceByPrimaryKey(id);
        StrategySequenceValue sequenceValue = strategySequenceValueMapper.findSequenceValueBySequenceId(id);
        StrategySequenceOther sequenceOther = convertStrategySequenceVo(sequence,sequenceValue);
        return sequenceOther;
    }

    @Override
    public String getSequenceValueByStrategyId(Integer strategyId) {
        return strategySequenceValueMapper.getSequenceValueByStrategyId(strategyId);
    }

    @Override
    public Optional<Integer> checkSequenceByName(String name) {
        return Optional.of(strategySequenceMapper.checkSequenceByName(name));
    }

    @Override
    public Optional<Integer> checkSequenceById(Integer id) {
        return Optional.of(strategySequenceMapper.checkSequenceById(id));
    }

    private StrategySequenceOther convertStrategySequenceVo(StrategySequence sequence, StrategySequenceValue sequenceValue) {
        StrategySequenceOther sequenceOther = new StrategySequenceOther();
        sequenceOther.setId(sequence.getId());
        sequenceOther.setName(sequence.getName());
        sequenceOther.getSortNum(sequenceValue.getSortNum());
        sequenceOther.setTip(sequence.getTip());
        sequenceOther.setValue(sequenceValue.getValue());
        sequenceOther.setStatus(sequence.getStatus());
        return sequenceOther;
    }

}
