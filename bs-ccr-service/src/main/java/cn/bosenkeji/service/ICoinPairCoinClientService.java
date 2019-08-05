package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairCoinClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ICoinPairCoinClientService
 * @Author CAJR
 * @create 2019/7/11 14:31
 */
@FeignClient( name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ICoinPairCoinClientServiceFallbackFactory.class)
public interface ICoinPairCoinClientService {

    @GetMapping("/coin_pair_coin/")
    public PageInfo listByPage(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);

    @GetMapping("/coin_pair_coin/list_by_coinId")
    public List<CoinPairCoin> listByCoinId(@RequestParam("coinId") int coinId);

    @PostMapping("/coin_pair_coin/")
    public Result addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) ;

    @PutMapping("/coin_pair_coin/")
    public Result updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin);

    @DeleteMapping("/coin_pair_coin/")
    public Result deleteCoinPairCoin(@RequestParam("coinId") int coinId,@RequestParam("coinPairId") int coinPairId);
}
