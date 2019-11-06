package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradeOrderClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/6 11:29 上午
 */
@Component
public class ITradeOrderClientServiceFallbackFactory implements FallbackFactory<ITradeOrderClientService> {


    @Override
    public ITradeOrderClientService create(Throwable throwable) {
        return new ITradeOrderClientService() {
            @Override
            public PageInfo listByPage(int pageNum, int pageSizeCommon) {
                TradeOrder tradeOrder = new TradeOrder();
                List<TradeOrder> tradeOrders = new ArrayList<>();
                tradeOrder.setId(0);
                tradeOrders.add(tradeOrder);
                return new PageInfo<>(tradeOrders);
            }

            @Override
            public TradeOrder getById(int id) {
                TradeOrder tradeOrder = new TradeOrder();
                tradeOrder.setId(0);
                return tradeOrder;
            }

            @Override
            public Result addOneOrderGroup(TradeOrder tradeOrder) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public Result delete(int id) {
                return new Result<>(-1,"hystrix");
            }
        };
    }
}
