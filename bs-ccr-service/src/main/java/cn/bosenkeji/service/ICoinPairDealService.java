package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairDealServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "bs-ccr-provider-transaction",configuration = FeignClientConfig.class,fallbackFactory = ICoinPairDealServiceFallbackFactory.class)
public interface ICoinPairDealService {

    @GetMapping("/coinpairdeal/{userId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "pageNum" ,defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @GetMapping("/coinpairdeal/{userId}/choic/{choicId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choicId") Integer choicId,
            @RequestParam(value = "pageNum" ,defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @GetMapping("/coinpairdeal/{userId}/type/{type}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type,
            @RequestParam(value = "pageNum" ,defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @PutMapping("/coinpairdeal/status")
    boolean updateCoinPairDealStartsById(
            @RequestParam("id") Integer id,
            @RequestParam("status") Integer status);

    @GetMapping("/coinpairdeal/count/{userId}")
    int countCoinPair(@PathVariable("userId") Integer userId);

    @GetMapping("/coinpairdeal/count/{userId}/choic/{choicId}")
    int countCoinPairDeal(@PathVariable("userId") Integer userId,@PathVariable("choicId") Integer choicId);

}
