package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiClientServiceFallbackFactory;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApi;
import cn.bosenkeji.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 10:37
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiClientServiceFallbackFactory.class)
public interface ITradePlatformApiClientService {

    @GetMapping("/trade_platform_apis/{id}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("id") int id);

    /**
     *
     * @param user
     * @param tradePlatformApi
     * @return boolean
     */
    @PostMapping("/trade_platform_apis/")
    public Optional<Integer> addOneTradePlatformApi(@RequestParam("userId") int user, @RequestBody TradePlatformApi tradePlatformApi);

    @PutMapping("/trade_platform_apis/")
    public Optional<Integer> updateTradePlatform(@RequestBody TradePlatformApi tradePlatformApi);

    @DeleteMapping("/trade_platform_apis/{tradePlatformId}")
    public Optional<Integer> deleteOneTradePlatform(@PathVariable("tradePlatformId") int tradePlatformId);
}
