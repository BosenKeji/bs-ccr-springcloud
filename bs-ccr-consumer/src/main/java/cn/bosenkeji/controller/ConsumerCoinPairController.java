package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.CoinPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:43
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerCoinPairController {

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @GetMapping("/coinpair/{id}")
    public Object getCoinPair(@PathVariable("id") int id) {
        return iCoinPairClientService.getCoinPair(id);
    }

    @GetMapping("/coinpair")
    public  Object listCoinPair() {
        return iCoinPairClientService.listCoinPair();
    }

    @PostMapping("/coinpair")
    public Object addCoinPair(@RequestBody CoinPair coinPair) {

        return iCoinPairClientService.addCoinPair(coinPair);
    }

    @PutMapping("/coinpair")
    public Object updateCoinPair(@RequestBody CoinPair coinPair){
        return iCoinPairClientService.updateCoinPair(coinPair);
    }

    @DeleteMapping("/coinpair/{id}")
    public Object deleteCoinPair(@PathVariable int id){
        return iCoinPairClientService.deleteCoinPair(id);
    }
}
