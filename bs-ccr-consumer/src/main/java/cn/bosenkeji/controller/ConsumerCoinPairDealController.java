package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/coinpairdeal")
public class ConsumerCoinPairDealController {

    @Resource
    private ICoinPairDealService coinPairDealService;

    @GetMapping(value = "/{userId}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(@PathVariable("userId") Integer userId) {
        return coinPairDealService.findCoinPairDealByUserId(userId, 0 ,10);
    }

    @GetMapping(value = "/{userId}/choic/{choicId}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choicId") Integer choicId
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndChoicId(userId,choicId,0,10);
    }


    @GetMapping(value = "/{userId}/type/{type}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") @Min(1) Integer userId,
            @PathVariable("type") @Min(1) @Max(2) Integer type
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndType(userId,type,0,10);
    }

    @PutMapping(value = "/status")
    public boolean updateCoinPairDealStartsById(@RequestParam("id") Integer id ,@RequestParam("status") Integer status) {
        return coinPairDealService.updateCoinPairDealStartsById(id,status);
    }

    @GetMapping(value = "/count/{userId}")
    public int countCoinPair(@PathVariable("userId") Integer userId) {
        return coinPairDealService.countCoinPair(userId);
    }

    @GetMapping(value = "/count/{userId}/choic/{choicId}")
    public int countCoinPairDeal(@PathVariable("userId")Integer userId, @PathVariable("choicId") Integer choicId) {
        return coinPairDealService.countCoinPairDeal(userId,choicId);
    }

}
