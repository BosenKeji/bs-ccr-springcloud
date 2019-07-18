package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairCoinClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ICoinPairCoinClientService
 * @Author CAJR
 * @create 2019/7/11 14:31
 */
@FeignClient( name = "bs-ccr-provider-coin",configuration = FeignClientConfig.class,fallbackFactory = ICoinPairCoinClientServiceFallbackFactory.class)
public interface ICoinPairCoinClientService {

    @GetMapping("/coinpaircoin/{id}")
    public CoinPairCoin getCoinPairCoin(@PathVariable("id") int id);

    @GetMapping("/coinpaircoin/")
    public PageInfo listCoinPairCoin(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                     @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coinpaircoin/")
    public boolean addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) ;

    @PutMapping("/coinpaircoin/")
    public boolean updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin);

    @DeleteMapping("/coinpaircoin/{id}")
    public boolean deleteCoinPairCoin(@PathVariable("id") int id);
}
