package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Optional;

@RestController
@RequestMapping("/coin_pair_deal")
public class ConsumerCoinPairDealController {

    @Resource
    private ICoinPairDealService coinPairDealService;

    @GetMapping(value = "/{userId}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize

    ) {
        return coinPairDealService.findCoinPairDealByUserId(userId, pageNum ,pageSize);
    }

    @GetMapping(value = "/{userId}/choice/{choiceId}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choiceId") Integer choiceId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndChoiceId(userId,choiceId,pageNum,pageSize);
    }


    @GetMapping(value = "/{userId}/type/{type}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") Integer userId,
            @PathVariable("type") Integer type,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndType(userId,type,pageNum,pageSize);
    }

    @PutMapping(value = "/status")
    public Optional<Integer> updateCoinPairDealStartsById(@RequestParam("id") Integer id , @RequestParam("status") Integer status) {
        return coinPairDealService.updateCoinPairDealStartsById(id,status);
    }

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
        return coinPairDealService.insertCoinPairDealBySelective(coinPairDeal);
    }

    @GetMapping(value = "/count/{userId}")
    public Optional<Integer> countCoinPair(@PathVariable("userId") Integer userId) {
        return coinPairDealService.countCoinPair(userId);
    }

    @GetMapping(value = "/count/{userId}/choice/{choiceId}")
    public Optional<Integer> countCoinPairDeal(@PathVariable("userId")Integer userId, @PathVariable("choiceId") Integer choiceId) {
        return coinPairDealService.countCoinPairDeal(userId,choiceId);
    }


    @DeleteMapping("/{id}")
    public Optional<Integer> deleteCoinPairDealByPrimaryKey(@PathVariable("id") Integer id) {
        return coinPairDealService.deleteCoinPairDealByPrimaryKey(id);
    }

    @DeleteMapping(value = "/{userId}/choice/{choiceId}")
    public Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(
            @PathVariable("userId") Integer userId, @PathVariable("choiceId") Integer choiceId
    ) {
        return coinPairDealService.deleteBatchCoinPairDealByUserIdAndChoiceId(userId,choiceId);
    }

}
