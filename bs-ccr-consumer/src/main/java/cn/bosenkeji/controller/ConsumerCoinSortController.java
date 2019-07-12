package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.vo.CoinSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerCoinSortController {

    @Autowired
    ICoinSortClientService iCoinSortClientService;

    @GetMapping("/coinsort/{id}")
    public Object getCoinSort(@PathVariable("id") int id) {
        return iCoinSortClientService.getCoinSort(id);
    }

    @GetMapping("/coinsort")
    public  Object listProduct() {
        return iCoinSortClientService.listCoinSort();
    }

    @PostMapping("/coinsort")
    public Object addCoin(@RequestBody CoinSort coinSort) {

        return iCoinSortClientService.addCoinSort(coinSort);
    }

    @PutMapping("/coinsort")
    public Object updateCoin(@RequestBody CoinSort coinSort){
        return iCoinSortClientService.updateCoinSort(coinSort);
    }

    @DeleteMapping("/coinsort/{id}")
    public Object deleteCoinSort(@PathVariable("id") int id){
        return iCoinSortClientService.deleteCoinSort(id);
    }
}
