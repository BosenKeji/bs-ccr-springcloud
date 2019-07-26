package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiBindProductComboClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.ITradePlatformApiClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 18:08
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiBindProductComboClientServiceFallbackFactory.class)
public interface ITradePlatformApiBindProductComboClientService {

    @GetMapping("/trade_platform_api_bind_product_combo/{id}")
    TradePlatformApiBindProductCombo getTradePlatformApiBindProductCombo(@PathVariable("userId") int userId);

    @PostMapping("/trade_platform_api_bind_product_combo/")
    Result addTradePlatformApiBindProductCombo(@RequestBody TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);
}
