package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.CoinPairCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerCoinPairCoinController {

    @Autowired
    ICoinPairCoinClientService iCoinPairCoinClientService;

    @GetMapping("/coinpaircoin/{id}")
    public Object getCoinPairCoin(@PathVariable("id") int id) {
        return iCoinPairCoinClientService.getCoinPairCoin(id);
    }

    @GetMapping("/coinpaircoin")
    public  Object listCoinPairCoin() {
        return iCoinPairCoinClientService.listCoinPairCoin();
    }

    @PostMapping("/coinpaircoin")
    public Object addCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin) {

        return iCoinPairCoinClientService.addCoinPairCoin(coinPairCoin);
    }

    @PutMapping("/coinpaircoin")
    public Object updateCoinPairCoin(@RequestBody CoinPairCoin coinPairCoin){
        return iCoinPairCoinClientService.updateCoinPairCoin(coinPairCoin);
    }

    @DeleteMapping("/coinpaircoin/{id}")
    public Object deleteCoinPairCoin(@PathVariable int id){
        return iCoinPairCoinClientService.deleteCoinPairCoin(id);
    }
}
