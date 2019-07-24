package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class IStrategyServiceFallbackFactory implements FallbackFactory<IStrategyService> {
    @Override
    public IStrategyService create(Throwable throwable) {
        return new IStrategyService() {
            @Override
            public StrategyOther getStrategy(Integer id) {
                StrategyOther strategyVO = new StrategyOther();
                strategyVO.setName("strategy hystrix");
                return strategyVO;
            }

            @Override
            public PageInfo<Strategy> listByPage(Integer pageNum, Integer pageSize) {
                PageInfo pageInfo = new PageInfo();
                List<Strategy> list = new ArrayList<>();
                Strategy strategy = new Strategy();
                strategy.setName("strategy hystrix");
                list.add(strategy);
                pageInfo.setList(list);
                return pageInfo;
            }

            @Override
            public Result addStrategyBySelective(Strategy strategy) {
                Optional<Integer> result = Optional.of(0);
                return new Result(result,"failed");
            }

            @Override
            public Result addStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
                Optional<Integer> result = Optional.of(0);
                return new Result(result,"failed");
            }
        };
    }
}
