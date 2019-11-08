package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IOrderGroupClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.OrderGroup;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author CAJR
 * @date 2019/11/6 11:25 上午
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data" ,configuration = FeignClientConfig.class,fallbackFactory = IOrderGroupClientServiceFallbackFactory.class)
public interface IOrderGroupClientService {

    @GetMapping("/order_group/")
    public PageInfo listByPageAndCoinPairChoiceId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                  @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                                                  @RequestParam("coinPairChoiceId") int coinPairChoiceId);

    @GetMapping("/order_group/{id}")
    public OrderGroup getById(@PathVariable("id") int id);

    @PostMapping("/order_group/")
    public Result addOneOrderGroup(@RequestBody OrderGroup orderGroup);

    @PutMapping("/order_group/")
    public Result updateOneOrderGroup(@RequestBody OrderGroup orderGroup);

    @DeleteMapping("/order_group/{id}")
    public Result deleteOne(@PathVariable("id") int id);

    @GetMapping("/order_group/search_group")
    public Result searchTradeRecordByCondition(@RequestParam(value = "startTime") Long startTime,
                                               @RequestParam(value = "endTime") Long endTime,
                                               @RequestParam("coinPairChoiceId") int coinPairChoiceId);
}
