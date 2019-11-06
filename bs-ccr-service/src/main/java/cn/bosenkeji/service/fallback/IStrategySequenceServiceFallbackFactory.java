package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IStrategySequenceService;
import cn.bosenkeji.util.Result;
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
            public Result insertStrategySequenceBySelective(StrategySequence sequence) {
                return new Result<>(Optional.of(0),"failed");
            }

            @Override
            public Result insertStrategySequenceValueBySelective(StrategySequenceValue sequenceValue) {
                return new Result<>(Optional.of(0),"failed");
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
            public Result<StrategySequenceOther> findSequenceByPrimaryKey(Integer id) {

                StrategySequenceOther sequenceOther = new StrategySequenceOther();
                sequenceOther.setName("strategy sequence hystrix");
                return new Result<>(sequenceOther);
            }

        };
    }
}
