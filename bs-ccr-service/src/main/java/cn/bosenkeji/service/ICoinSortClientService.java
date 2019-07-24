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
@FeignClient( name = "bs-ccr-provider-coin",configuration = FeignClientConfig.class,fallbackFactory = ICoinSortClientServiceFallbackFactory.class)
public interface ICoinSortClientService {

    @GetMapping("/coin_sort/{id}")
    public CoinSort getCoinSort(@PathVariable("id") int id);

    @GetMapping("/coin_sort/")
    public PageInfo listCoinSort(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin_sort/")
    public Result addCoinSort(@RequestBody CoinSort coinSort) ;

    @PutMapping("/coin_sort/")
    public Result updateCoinSort(@RequestBody CoinSort coinSort);

    @DeleteMapping("/coin_sort/{id}")
    public Result deleteCoinSort(@PathVariable("id") int id);

}
