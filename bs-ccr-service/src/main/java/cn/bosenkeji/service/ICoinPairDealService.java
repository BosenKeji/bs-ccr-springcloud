package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairDealServiceFallbackFactory;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@FeignClient(value = "bs-ccr-provider-transaction",configuration = FeignClientConfig.class
        , fallbackFactory = ICoinPairDealServiceFallbackFactory.class
)
public interface ICoinPairDealService {

    @GetMapping("/coin_pair_deal/{userId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/coin_pair_deal/{userId}/choic/{choiceId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choiceId") Integer choicId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
            );

    @GetMapping("/coin_pair_deal/{userId}/type/{type}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type,
            @RequestParam("pageNum" ) Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PutMapping("/coin_pair_deal/status")
    Optional<Integer> updateCoinPairDealStartsById(
            @RequestParam("id") Integer id,
            @RequestParam("status") Integer status);

    @RequestMapping(value = "/coin_pair_deal/",method = RequestMethod.POST)
    Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    @GetMapping("/coin_pair_deal/count/{userId}")
    Optional<Integer> countCoinPair(@PathVariable("userId") Integer userId);

    @GetMapping("/coin_pair_deal/count/{userId}/choice/{choiceId}")
    Optional<Integer> countCoinPairDeal(@PathVariable("userId") Integer userId,@PathVariable("choiceId") Integer choicId);

    @DeleteMapping("/coin_pair_deal/{id}")
    Optional<Integer> deleteCoinPairDealByPrimaryKey(@PathVariable("id") Integer id);

    @DeleteMapping("/coin_pair_deal/{userId}/choice/{choiceId}")
    Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(@PathVariable("userId") Integer userId, @PathVariable("choiceId") Integer choicId);

}
