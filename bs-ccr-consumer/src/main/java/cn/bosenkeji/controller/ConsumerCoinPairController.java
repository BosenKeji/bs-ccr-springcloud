package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.CoinPair;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:43
 */
@RestController
@RequestMapping("/consumer/coinpair")
public class ConsumerCoinPairController {

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @GetMapping("/{id}")
    public CoinPair getCoinPair(@PathVariable("id") int id) {
        return iCoinPairClientService.getCoinPair(id);
    }

    @GetMapping("/")
    public PageInfo listCoinPair(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinPairClientService.listCoinPair(pageNum, pageSizeCommon);
    }

    @PostMapping("/")
    public boolean addCoinPair(@RequestBody CoinPair coinPair) {

        return iCoinPairClientService.addCoinPair(coinPair);
    }

    @PutMapping("/")
    public boolean updateCoinPair(@RequestBody CoinPair coinPair){
        return iCoinPairClientService.updateCoinPair(coinPair);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoinPair(@PathVariable int id){
        return iCoinPairClientService.deleteCoinPair(id);
    }
}
