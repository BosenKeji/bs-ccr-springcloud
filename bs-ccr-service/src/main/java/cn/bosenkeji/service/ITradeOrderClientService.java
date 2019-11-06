package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradeOrderClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageInfo;
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
}
