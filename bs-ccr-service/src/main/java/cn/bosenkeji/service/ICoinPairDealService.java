package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinPairDealServiceFallbackFactory;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(value = "bs-ccr-provider-transaction",configuration = FeignClientConfig.class
        , fallbackFactory = ICoinPairDealServiceFallbackFactory.class
)
public interface ICoinPairDealService {

    @GetMapping("/coinpairdeal/{userId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/coinpairdeal/{userId}/choic/{choicId}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choicId") Integer choicId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
            );

    @GetMapping("/coinpairdeal/{userId}/type/{type}")
    PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type,
            @RequestParam("pageNum" ) Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PutMapping("/coinpairdeal/status")
    boolean updateCoinPairDealStartsById(
            @RequestParam("id") Integer id,
            @RequestParam("status") Integer status);

    @RequestMapping(value = "/coinpairdeal/",method = RequestMethod.POST)
    boolean insertCoinPairDealBySelective(CoinPairDeal coinPairDeal);

    @GetMapping("/coinpairdeal/count/{userId}")
    int countCoinPair(@RequestParam("userId") Integer userId);

    @GetMapping("/coinpairdeal/count/{userId}/choic/{choicId}")
    int countCoinPairDeal(@RequestParam("userId") Integer userId,@RequestParam("choicId") Integer choicId);

    @DeleteMapping("/coinpairdeal/{id}")
    boolean deleteCoinPairDealByPrimaryKey(@PathVariable("id") Integer id);

    @DeleteMapping("/coinpairdeal/{userId}/choic/{choicId}")
    boolean deleteBatchCoinPairDealByUserIdAndChoicId(@PathVariable("userId") Integer userId, @PathVariable("choicId") Integer choicId);

}
