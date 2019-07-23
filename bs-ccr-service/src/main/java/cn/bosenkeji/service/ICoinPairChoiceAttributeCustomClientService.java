package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoiceAttributeCustomClientServiceFallbackFactory;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 14:52
 */

@FeignClient(name = "bs-ccr-provider-transaction" ,configuration = FeignClientConfig.class,fallbackFactory = ICoinPairChoiceAttributeCustomClientServiceFallbackFactory.class)
public interface ICoinPairChoiceAttributeCustomClientService {

    @GetMapping("/coin_pair_choice_attribute_custom/{coinPairChoiceId}")
    public CoinPairChoiceAttributeCustom getCoinPairChoiceAttributeCustomByCoinPairChoiceId(@PathVariable("coinPairChoiceId") int coinPairChoiceId);

    @PostMapping("/coin_pair_choice_attribute_custom/")
    public Optional<Integer> settingParameters(@RequestBody CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    @PutMapping("/coin_pair_choice_attribute_custom/")
    public Optional<Integer> updateCoinPairChoiceAttributeCustom(@RequestBody CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom);

    @DeleteMapping("/coin_pair_choice_attribute_custom/{coinPairChoiceId}")
    public Optional<Integer> deleteOneCoinPairChoiceAttributeCustomByCoinPairChoiceId(@PathVariable("coinPairChoiceId") int coinPairChoiceId);
}
