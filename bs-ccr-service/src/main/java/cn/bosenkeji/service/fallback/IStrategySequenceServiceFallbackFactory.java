package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IStrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;

import java.util.ArrayList;
import java.util.List;

public class IStrategySequenceServiceFallbackFactory implements FallbackFactory<IStrategySequenceService> {
    @Override
    public IStrategySequenceService create(Throwable throwable) {
        return new IStrategySequenceService() {
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
            public StrategySequenceVO findSequenceByPrimaryKey(Integer id) {
                StrategySequenceVO sequenceVO = new StrategySequenceVO();
                sequenceVO.setName("strategy sequence hystrix");
                return sequenceVO;
            }

            @Override
            public String getSequenceValueByStrategyId(Integer strategyId) {
                return "strategy sequence hystrix";
            }
        };
    }
}
