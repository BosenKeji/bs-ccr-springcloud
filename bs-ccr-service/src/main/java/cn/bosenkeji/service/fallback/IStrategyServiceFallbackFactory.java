package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IStrategyServiceFallbackFactory implements FallbackFactory<IStrategyService> {
    @Override
    public IStrategyService create(Throwable throwable) {
        return new IStrategyService() {
            @Override
            public StrategyVO getStrategy(Integer id) {
                StrategyVO strategyVO = new StrategyVO();
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
            public boolean addStrategyBySelective(Strategy strategy) {
                return false;
            }

            @Override
            public boolean addStrategyAttributeBySelective(StrategyAttribute strategyAttribute) {
                return false;
            }
        };
    }
}
