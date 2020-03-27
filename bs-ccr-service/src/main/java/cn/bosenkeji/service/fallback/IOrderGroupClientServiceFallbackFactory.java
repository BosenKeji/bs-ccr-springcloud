package cn.bosenkeji.service.fallback;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.service.IOrderGroupClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.*;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/6 11:30 上午
 */
@Component
public class IOrderGroupClientServiceFallbackFactory implements FallbackFactory<IOrderGroupClientService> {

    @Override
    public IOrderGroupClientService create(Throwable throwable) {
        return new IOrderGroupClientService() {
            @Override
            public PageInfo listByPageAndCoinPairChoiceId(int pageNum, int pageSizeCommon, int coinPairChoiceId) {
                OrderGroupOther orderGroupOther = new OrderGroupOther();
                List<OrderGroupOther> orderGroupOthers = new ArrayList<>();
                orderGroupOther.setId(0);
                orderGroupOther.setName("hystrix");
                orderGroupOthers.add(orderGroupOther);
                return new PageInfo<>(orderGroupOthers);
            }

            @Override
            public OrderGroup getById(int id) {
                OrderGroup o = new OrderGroup();
                o.setName("hystrix");
                return o;
            }

            @Override
            public Result addOneOrderGroup(OrderGroup orderGroup) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public Result updateOneOrderGroup(OrderGroup orderGroup) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public Result deleteOne(int id) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public OpenSearchPage searchTradeRecordByCondition(Long startTime, Long endTime, int coinPairChoiceId, int pageNum, int pageSize) {
                OpenSearchPage page = new OpenSearchPage();
                page.setTotal(0L);
                return page;
            }

            @Override
            public OrderGroupOverviewResult getCoinPairChoiceTradeOverview(int coinPairChoiceId) {
                OrderGroupOverviewResult o = new OrderGroupOverviewResult();
                o.setCoinPairName("hystrix");
                return o;
            }

            @Override
            public int getIdByName(String name) {
                return 0;
            }

            @Override
            public Result addOrUpdateOneOrderGroup(int id, String name, int coinPairChoiceId, double endProfitRatio, int isEnd, int endType, int sign) {
                return new Result<>(-1,"hystrix");
            }
        };
    }
}
