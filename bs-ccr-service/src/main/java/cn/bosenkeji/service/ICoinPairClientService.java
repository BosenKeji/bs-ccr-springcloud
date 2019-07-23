package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairClientServiceFallbackFactory;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 11:29
 */
@FeignClient(name = "bs-ccr-provider-coin", configuration = FeignClientConfig.class ,fallbackFactory = ICoinPairClientServiceFallbackFactory.class)
public interface ICoinPairClientService {


    @GetMapping("/coin_pair/{id}")
    public CoinPair getCoinPair(@PathVariable("id") int id);

    @GetMapping("/coin_pair/")
    public PageInfo listCoinPair(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin_pair/")
    public boolean addCoinPair(@RequestBody CoinPair coinPair) ;

    @PutMapping("/coin_pair/")
    public boolean updateCoinPair(@RequestBody CoinPair coinPair);

    @DeleteMapping("/coin_pair/{id}")
    public boolean deleteCoinPair(@PathVariable("id") int id );

}
