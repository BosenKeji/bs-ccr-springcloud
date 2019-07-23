package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
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
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") Integer userId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize

    ) {
        return coinPairDealService.findCoinPairDealByUserId(userId, pageNum ,pageSize);
    }

    @GetMapping(value = "/{userId}/choic/{choicId}")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") Integer userId,
            @PathVariable("choicId") Integer choicId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndChoicId(userId,choicId,pageNum,pageSize);
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


    @DeleteMapping("/{id}")
    public boolean deleteCoinPairDealByPrimaryKey(@PathVariable("id") Integer id) {
        return coinPairDealService.deleteCoinPairDealByPrimaryKey(id);
    }

    @DeleteMapping(value = "/{userId}/choic/{choicId}")
    public boolean deleteBatchCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") Integer userId, @PathVariable("choicId") Integer choicId
    ) {
        return coinPairDealService.deleteBatchCoinPairDealByUserIdAndChoicId(userId,choicId);
    }

}
