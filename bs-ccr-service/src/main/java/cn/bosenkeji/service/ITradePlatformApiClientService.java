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
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiClientServiceFallbackFactory.class)
public interface ITradePlatformApiClientService {

    @GetMapping("/trade_platform_apis/{tradePlatformId}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("tradePlatformId") int tradePlatformId);

    /**
     *
     * @param user
     * @param tradePlatformApi
     * @return boolean
     */
    @PostMapping("/trade_platform_apis/")
    public Result addOneTradePlatformApi(@RequestParam("userId") int user, @RequestBody TradePlatformApi tradePlatformApi);

    @PutMapping("/trade_platform_apis/")
    public Result updateTradePlatform(@RequestBody TradePlatformApi tradePlatformApi);

    @DeleteMapping("/trade_platform_apis/{tradePlatformId}")
    public Result deleteOneTradePlatform(@PathVariable("tradePlatformId") int tradePlatformId);

    @GetMapping("/trade_platform_apis/user/{userId}")
    TradePlatformApi selectByUserId(@PathVariable("userId") Integer userId);
}
