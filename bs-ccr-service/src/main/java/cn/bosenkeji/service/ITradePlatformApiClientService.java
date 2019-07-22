package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiClientServiceFallbackFactory;
import cn.bosenkeji.vo.TradePlatformApi;
import cn.bosenkeji.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:37
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiClientServiceFallbackFactory.class)
public interface ITradePlatformApiClientService {

    @GetMapping("/tradeplatformapis/{id}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("id") int id);

    /**
     *
     * @param user
     * @param tradePlatformApi
     * @return boolean
     */
    @PostMapping("/tradeplatformapis/")
    public boolean addOneTradePlatformApi(@RequestParam("user") User user,@RequestParam("tradePlatformApi") TradePlatformApi tradePlatformApi);

    @PutMapping("/tradeplatformapis/")
    public boolean updateTradePlatform(@RequestBody TradePlatformApi tradePlatformApi);

    @DeleteMapping("/tradeplatformapis/{tradePlatformId}")
    public boolean deleteOneTradePlatform(@PathVariable("tradePlatformId") int tradePlatformId);
}
