package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoicAttributeClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import com.netflix.client.http.HttpRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 14:50
 */

@FeignClient(name = "bs-ccr-provider-transaction" ,configuration = FeignClientConfig.class,fallbackFactory = ICoinPairChoicAttributeClientServiceFallbackFactory.class)
public interface ICoinPairChoicAttributeClientService {
    @GetMapping("/coinpairchoicattribute/{coinPartnerChoicId}")
    public CoinPairChoicAttribute getCoinPairChoicAttributeByCoinPartnerChoicID(@PathVariable("coinPartnerChoicId") int coinPartnerChoicId);

    @PostMapping("/coinpairchoicattribute/")
    public boolean addOneCoinPairChoicAttribute(@RequestParam(value = "coinPairChoicIds[]")int[] coinPairChoicIds,
                                                @RequestParam("lever")  int lever,
                                                @RequestParam("money") int money ,
                                                @RequestParam("isCustom")  int isCustom);


    @PutMapping("/coinpairchoicattribute/")
    public boolean updateCoinPairChoicAttribute(@RequestBody CoinPairChoicAttribute coinPairChoicAttribute);


    @PostMapping("/coinpairchoicattribute/{coinPartnerChoicId}")
    public boolean deleteOneCoinPairChoicAttribute(@PathVariable("coinPartnerChoicId") int coinPartnerChoicId);

}
