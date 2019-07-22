package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoicAttributeCustomClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 14:52
 */

@FeignClient(name = "bs-ccr-provider-transaction" ,configuration = FeignClientConfig.class,fallbackFactory = ICoinPairChoicAttributeCustomClientServiceFallbackFactory.class)
public interface ICoinPairChoicAttributeCustomClientService {

    @GetMapping("/coinpairchoicattributecustom/{coinPairChoicId}")
    public CoinPairChoicAttributeCustom getCoinPairChoicAttributeCustomByCoinPairChoicId(@PathVariable("coinPairChoicId") int coinPairChoicId);

    @PostMapping("/coinpairchoicattributecustom/")
    public boolean settingParameters(@RequestBody CoinPairChoicAttributeCustom coinPairChoicAttributeCustom);

    @PutMapping("/coinpairchoicattributecustom/")
    public boolean updateCoinPairChoicAttributeCustom(@RequestBody CoinPairChoicAttributeCustom coinPairChoicAttributeCustom);

    @DeleteMapping("/coinpairchoicattributecustom/{coinPairChoicId}")
    public boolean deleteOneCoinPairChoicAttributeCustomByCoinPairChoicId(@PathVariable("coinPairChoicId") int coinPairChoicId);
}
