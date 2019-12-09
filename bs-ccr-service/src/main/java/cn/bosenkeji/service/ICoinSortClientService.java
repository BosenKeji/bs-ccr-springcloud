package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinSortClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName ICoinSortClientService
 * @Author CAJR
 * @create 2019/7/11 14:49
 */
@FeignClient( name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ICoinSortClientServiceFallbackFactory.class)
public interface ICoinSortClientService {

    @GetMapping("/coin_sort/{tradePlatformId}")
    public PageInfo listCoinSortByTradePlatformId(@PathVariable("tradePlatformId") int tradePlatformId,
                                 @RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin_sort/")
    public Result addCoinSort(@RequestParam("tradePlatformName") String tradePlatformName,
                              @RequestParam( value="coinId")int coinId, @RequestParam( value="type") int type) ;

    @PutMapping("/coin_sort/")
    public Result updateCoinSort(@RequestBody CoinSort coinSort);

    @DeleteMapping("/coin_sort/")
    public Result deleteCoinSort(@RequestParam("tradePlatformId") int tradePlatformId,
                                 @RequestParam("coinId") int coinId);

}
