package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairCoinClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName ICoinPairCoinClientService
 * @Author CAJR
 * @create 2019/7/11 14:31
 */
@FeignClient( name = "bs-ccr-provider-coin",configuration = FeignClientConfig.class,fallbackFactory = ICoinPairCoinClientServiceFallbackFactory.class)
public interface ICoinPairCoinClientService {

    @PostMapping("/coin_pair_coin/")
    public Result addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) ;

    @PutMapping("/coin_pair_coin/")
    public Result updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin);

    @DeleteMapping("/coin_pair_coin/")
    public Result deleteCoinPairCoin(@RequestParam("coinId") int coinId,@RequestParam("coinPairId") int coinPairId);
}
