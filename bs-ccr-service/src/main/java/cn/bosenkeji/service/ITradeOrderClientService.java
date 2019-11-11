package cn.bosenkeji.service;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradeOrderClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.SumShellProfitAggregateVo;
import cn.bosenkeji.vo.transaction.SumTradeCostAggregateVo;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author CAJR
 * @date 2019/11/6 11:26 上午
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data" ,configuration = FeignClientConfig.class
        ,fallbackFactory = ITradeOrderClientServiceFallbackFactory.class
)
public interface ITradeOrderClientService {

    @GetMapping("/trade_order/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);

    @GetMapping("/trade_order/{id}")
    public TradeOrder getById(@PathVariable("id") int id);

    @PostMapping("/trade_order/")
    public Result addOneOrderGroup(@RequestBody TradeOrder tradeOrder);

    @DeleteMapping("/trade_order/{id}")
    public Result delete(@PathVariable("id") int id);

    @GetMapping("/trade_order/by_condition")
    public OpenSearchPage searchTradeOrderByCondition(@RequestParam("coinPairChoiceIds") String coinPairChoiceIds,
                                                      @RequestParam(value = "tradeType",defaultValue = "0") Integer tradeType,
                                                      @RequestParam(value = "startTime",defaultValue = "0") Long startTime,
                                                      @RequestParam(value = "endTime",defaultValue = "0") Long endTime,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value="pageSize",defaultValue="12") int pageSize
    );


    @GetMapping("/trade_order/total_trade_cost_by_condition")
    SumTradeCostAggregateVo getTotalTradeCostByCondition(@RequestParam("coinPairChoiceIds")  String coinPairChoiceIds,
                                                                @RequestParam(value = "tradeType",defaultValue = "0") Integer tradeType);

    @GetMapping("/trade_order/total_shell_profit_by_condition")
    SumShellProfitAggregateVo getTotalShellProfitByCondition(@RequestParam("coinPairChoiceIds") String coinPairChoiceIds);
}
