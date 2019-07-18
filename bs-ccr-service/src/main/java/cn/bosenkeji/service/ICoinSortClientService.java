package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinSortClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinSort;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ICoinSortClientService
 * @Author CAJR
 * @create 2019/7/11 14:49
 */
@FeignClient( name = "bs-ccr-provider-coin",configuration = FeignClientConfig.class,fallbackFactory = ICoinSortClientServiceFallbackFactory.class)
public interface ICoinSortClientService {

    @GetMapping("/coinsort/{id}")
    public CoinSort getCoinSort(@PathVariable("id") int id);

    @GetMapping("/coinsort/")
    public PageInfo listCoinSort(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coinsort/")
    public boolean addCoinSort(@RequestBody CoinSort coinSort) ;

    @PutMapping("/coinsort/")
    public boolean updateCoinSort(@RequestBody CoinSort coinSort);

    @DeleteMapping("/coinsort/{id}")
    public boolean deleteCoinSort(@PathVariable("id") int id);

}
