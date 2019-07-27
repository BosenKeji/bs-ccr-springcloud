package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoiceAttributeClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 14:50
 */

@FeignClient(name = "bs-ccr-provider-transaction" ,configuration = FeignClientConfig.class,fallbackFactory = ICoinPairChoiceAttributeClientServiceFallbackFactory.class)
public interface ICoinPairChoiceAttributeClientService {
    @GetMapping("/coin_pair_choice_attribute/{coinPartnerChoiceId}")
    public CoinPairChoiceAttribute getCoinPairChoiceAttributeByCoinPartnerChoiceID(@PathVariable("coinPartnerChoiceId") int coinPartnerChoiceId);

    @PostMapping("/coin_pair_choice_attribute/")
    public Result addOneCoinPairChoiceAttribute(@RequestParam(value = "coinPairChoiceIdStr") String coinPairChoiceIdStr,
                                                @RequestParam("strategyId") int strategyId,
                                                @RequestParam("lever")  int lever,
                                                @RequestParam("money") int money ,
                                                @RequestParam("isCustom")  int isCustom);


    @PutMapping("/coin_pair_choice_attribute/")
    public Result updateCoinPairChoiceAttribute(@RequestBody CoinPairChoiceAttribute coinPairChoiceAttribute);


    @PostMapping("/coin_pair_choice_attribute/{coinPartnerChoiceId}")
    public Result deleteOneCoinPairChoiceAttribute(@PathVariable("coinPartnerChoiceId") int coinPartnerChoiceId);

}
