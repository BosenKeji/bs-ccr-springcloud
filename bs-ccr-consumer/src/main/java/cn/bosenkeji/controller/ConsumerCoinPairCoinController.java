package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/consumer/coinpaircoin")
public class ConsumerCoinPairCoinController {

    @Autowired
    ICoinPairCoinClientService iCoinPairCoinClientService;

    @GetMapping("/{id}")
    public CoinPairCoin getCoinPairCoin(@PathVariable("id") int id) {
        return iCoinPairCoinClientService.getCoinPairCoin(id);
    }

    @GetMapping("/")
    public PageInfo listCoinPairCoin(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                     @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinPairCoinClientService.listCoinPairCoin(pageNum,pageSizeCommon);
    }

    @PostMapping("/")
    public boolean addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) {

        return iCoinPairCoinClientService.addCoinPairCoin(coinPairCoin);
    }

    @PutMapping("/")
    public boolean updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin){
        return iCoinPairCoinClientService.updateCoinPairCoin(coinPairCoin);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoinPairCoin(@PathVariable int id){
        return iCoinPairCoinClientService.deleteCoinPairCoin(id);
    }
}
