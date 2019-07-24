package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairCoinClientServiceFallbackFactory;
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

    @GetMapping("/coin_pair_coin/{id}")
    public CoinPairCoin getCoinPairCoin(@PathVariable("id") int id);

    @GetMapping("/coin_pair_coin/")
    public PageInfo listCoinPairCoin(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                     @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin_pair_coin/")
    public Optional<Integer> addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) ;

    @PutMapping("/coin_pair_coin/")
    public Optional<Integer> updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin);

    @DeleteMapping("/coin_pair_coin/}")
    public Optional<Integer> deleteCoinPairCoin(@RequestParam("coinId") int coinId,@RequestParam("coinPairId") int coinPairId);
}
