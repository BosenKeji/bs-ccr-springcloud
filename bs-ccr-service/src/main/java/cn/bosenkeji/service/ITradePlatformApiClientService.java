package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:37
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiClientServiceFallbackFactory.class)
public interface ITradePlatformApiClientService {

    @GetMapping("/trade_platform_apis/{id}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("id") int id);

    @GetMapping("/trade_platform_apis/edit_trade_platform")
    public TradePlatformApi getOneTradePlatformApiByTradePlatformIdAndUserId(@RequestParam("tradePlatformId") int tradePlatformId,
                                                                             @RequestParam("userId") int userId);

    /**
     *
     * @param tradePlatformApi
     * @return boolean
     */
    @PostMapping("/trade_platform_apis/")
    public Result addOneTradePlatformApi(@RequestBody TradePlatformApi tradePlatformApi);

    @PutMapping("/trade_platform_apis/")
    public Result updateTradePlatformApi(@RequestBody TradePlatformApi tradePlatformApi);

    @DeleteMapping("/trade_platform_apis/{id}")
    public Result deleteOneTradePlatformApi(@PathVariable("id") int tradePlatformId);

    @GetMapping("/trade_platform_apis/user/{userId}")
    TradePlatformApi selectByUserId(@PathVariable("userId") Integer userId);
}
