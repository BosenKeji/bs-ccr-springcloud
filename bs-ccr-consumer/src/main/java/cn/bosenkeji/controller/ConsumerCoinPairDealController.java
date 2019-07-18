package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

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
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndType(userId,type,0,10);
    }

    @PutMapping(value = "/status")
    public boolean updateCoinPairDealStartsById(@RequestParam("id") Integer id ,@RequestParam("status") Integer status) {
        return coinPairDealService.updateCoinPairDealStartsById(id,status);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public boolean insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
        return coinPairDealService.insertCoinPairDealBySelective(coinPairDeal);
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
