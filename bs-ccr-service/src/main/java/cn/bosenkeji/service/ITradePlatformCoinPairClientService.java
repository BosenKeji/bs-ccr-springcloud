package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformCoinPairClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:37
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformCoinPairClientServiceFallbackFactory.class)
public interface ITradePlatformCoinPairClientService {

    @GetMapping("/trade_platform_coin_pairs/")
    public PageInfo listTradePlatformCoinPairs(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);

    @GetMapping("/trade_platform_coin_pairs/{id}")
    public TradePlatformCoinPair getOneTradePlatformCoinPair(@PathVariable("id") int id);

    @PostMapping("/trade_platform_coin_pairs/")
    public Result addOneTradePlatformCoinPair(@RequestBody TradePlatformCoinPair tradePlatformCoinPair);

    @PutMapping("/trade_platform_coin_pairs/")
    public Result updateTradePlatformCoinPair(@RequestBody TradePlatformCoinPair tradePlatformCoinPair);

    @DeleteMapping("/trade_platform_coin_pairs/")
    public Result deleteOneTradePlatformCoinPair(@RequestParam("id") int tradePlatformId, @RequestParam("id") int id);

}
