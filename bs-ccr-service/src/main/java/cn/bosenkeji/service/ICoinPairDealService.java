package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.transaction.CoinPairDealOther;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import cn.bosenkeji.service.fallback.ICoinPairDealServiceFallbackFactory;


@FeignClient(value = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class
        , fallbackFactory = ICoinPairDealServiceFallbackFactory.class
)
public interface ICoinPairDealService {

    @GetMapping("/coin_pair_deal/{userId}")
    PageInfo<CoinPairDealOther> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/coin_pair_deal/{userId}/choice/{choiceId}")
    PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndChoiceId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choiceId") Integer choiceId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
            );

    @GetMapping("/coin_pair_deal/{userId}/type/{type}")
    PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type,
            @RequestParam("pageNum" ) Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PutMapping("/coin_pair_deal/status")
    Result updateCoinPairDealStartsById(
            @RequestParam("id") Integer id,
            @RequestParam("status") Integer status);

    @RequestMapping(value = "/coin_pair_deal/",method = RequestMethod.POST)
    Result insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    @GetMapping("/coin_pair_deal/count/{userId}")
    Result countCoinPair(@PathVariable("userId") Integer userId);

    @GetMapping("/coin_pair_deal/count/{userId}/choice/{choiceId}")
    Result countCoinPairDeal(@PathVariable("userId") Integer userId,@PathVariable("choiceId") Integer choicId);

    @DeleteMapping("/coin_pair_deal/{id}")
    Result deleteCoinPairDealByPrimaryKey(@PathVariable("id") Integer id);

    @DeleteMapping("/coin_pair_deal/{userId}/choice/{choiceId}")
    Result deleteBatchCoinPairDealByUserIdAndChoiceId(@PathVariable("userId") Integer userId, @PathVariable("choiceId") Integer choicId);

}
