package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairCoinClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairCoin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ICoinPairCoinClientService
 * @Author CAJR
 * @create 2019/7/11 14:31
 */
@FeignClient( name = "BS-CCR-PROVIDER-COIN",configuration = FeignClientConfig.class,fallbackFactory = ICoinPairCoinClientServiceFallbackFactory.class)
public interface ICoinPairCoinClientService {

    @GetMapping("/coinpaircoin/{id}")
    public CoinPairCoin getCoinPairCoin(@PathVariable("id") int id);

    @GetMapping("/coinpaircoin")
    public List<CoinPairCoin> listCoinPairCoin() ;

    @PostMapping("/coinpaircoin")
    public boolean addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) ;

    @PutMapping("/coinpaircoin")
    public boolean updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin);

    @DeleteMapping("/coinpaircoin/{id}")
    public boolean deleteCoinPairCoin(@PathVariable("id") int id);
}
