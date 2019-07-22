package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairChoicClientServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPair;
import cn.bosenkeji.vo.CoinPairChoic;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 14:50
 */
@FeignClient(name = "bs-ccr-provider-transaction" ,configuration = FeignClientConfig.class,fallbackFactory = ICoinPairChoicClientServiceFallbackFactory.class)
public interface ICoinPairChoicClientService {

    @GetMapping("/coinpairchoic/")
    public PageInfo getListCoinPairChoicWithPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);

    @GetMapping("/coinpairchoic/{id}")
    public CoinPairChoic getOneCoinPairChoic(@PathVariable("id") int id);

    @PostMapping("/coinpairchoic/")
    public boolean addOneCoinPairChoic(@RequestParam("userId")  int user,
                                       @RequestParam("strategyStatus")  int strategyStatus,
                                       @RequestParam("coinPairId")   int coinPairId);

    @PutMapping("/coinpairchoic/")
    public boolean updateCoinPairChoic(@RequestBody CoinPairChoic coinPairChoic);

    @DeleteMapping("/coinpairchoic/{id}")
    public boolean deleteOneCoinPairChoic(@PathVariable("id") int id);
}
