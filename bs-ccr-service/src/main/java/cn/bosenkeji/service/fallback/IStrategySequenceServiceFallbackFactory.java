package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IStrategySequenceService;
import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceOther;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IStrategySequenceServiceFallbackFactory implements FallbackFactory<IStrategySequenceService> {
    @Override
    public IStrategySequenceService create(Throwable throwable) {
        return new IStrategySequenceService() {
            @Override
            public Optional<Integer> insertStrategySequenceBySelective(StrategySequence sequence) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> insertStrategySequenceValueBySelective(StrategySequenceValue sequenceValue) {
                return Optional.of(0);
            }

            @Override
            public PageInfo<StrategySequence> findAll(Integer pageNum, Integer pageSize) {
                PageInfo<StrategySequence> pageInfo = new PageInfo<>();
                StrategySequence sequence = new StrategySequence();
                sequence.setName("strategy sequence hystrix");
                List<StrategySequence> list = new ArrayList<>();
                list.add(sequence);
                pageInfo.setList(list);
                return pageInfo;
            }

            @Override
            public StrategySequenceOther findSequenceByPrimaryKey(Integer id) {
                StrategySequenceOther sequenceOther = new StrategySequenceOther();
                sequenceOther.setName("strategy sequence hystrix");
                return sequenceOther;
            }

            @Override
            public String getSequenceValueByStrategyId(Integer strategyId) {
                return "strategy sequence hystrix";
            }
        };
    }
}
