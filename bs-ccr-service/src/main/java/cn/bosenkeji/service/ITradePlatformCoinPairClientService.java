package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformCoinPairClientServiceFallbackFactory;
import cn.bosenkeji.vo.TradePlatform;
import cn.bosenkeji.vo.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:37
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformCoinPairClientServiceFallbackFactory.class)
public interface ITradePlatformCoinPairClientService {

    @GetMapping("/tradeplatformcoinpairs/")
    public PageInfo listTradePlatformCoinPairs(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);

    @GetMapping("/tradeplatformcoinpairs/{id}")
    public TradePlatformCoinPair getOneTradePlatformCoinPair(@PathVariable("id") int id);

    @PostMapping("/tradeplatformcoinpairs/")
    public boolean addOneTradePlatformCoinPair(@RequestBody TradePlatformCoinPair tradePlatformCoinPair);

    @PutMapping("/tradeplatformcoinpairs/")
    public boolean updateTradePlatformCoinPair(@RequestBody TradePlatformCoinPair tradePlatformCoinPair);

    @DeleteMapping("/tradeplatformcoinpairs/{id}")
    public boolean deleteOneTradePlatformCoinPair(@PathVariable("id") int id);

}
