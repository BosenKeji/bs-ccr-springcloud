package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPair;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 11:29
 */
@FeignClient(name = "bs-ccr-provider-coin", configuration = FeignClientConfig.class ,fallbackFactory = ICoinPairClientServiceFallbackFactory.class)
public interface ICoinPairClientService {


    @GetMapping("/coinpair/{id}")
    public CoinPair getCoinPair(@PathVariable("id") int id);

    @GetMapping("/coinpair/")
    public List<CoinPair> listCoinPair() ;

    @PostMapping("/coinpair/")
    public boolean addCoinPair(@RequestBody CoinPair coinPair) ;

    @PutMapping("/coinpair/")
    public boolean updateCoinPair(@RequestBody CoinPair coinPair);

    @DeleteMapping("/coinpair/{id}")
    public boolean deleteCoinPair(@PathVariable("id") int id );

}
