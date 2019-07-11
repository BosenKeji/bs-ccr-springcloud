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

    @GetMapping("/coinsort/get")
    public Object getProduct(int id) {
        return iCoinSortClientService.getCoinSort(id);
    }

    @GetMapping("/coinsort/list")
    public  Object listProduct() {
        return iCoinSortClientService.listCoinSort();
    }

    @PostMapping("/coinsort/add")
    public Object addCoin(CoinSort coinSort) {

        return iCoinSortClientService.addCoinSort(coinSort);
    }

    @PutMapping("/coin")
    public Object updateCoin(@RequestBody CoinSort coinSort){
        return iCoinSortClientService.updateCoinSort(coinSort);
    }

    @DeleteMapping("/coin/{id}")
    public Object deleteCoinSort(@PathVariable("id") int id){
        return iCoinSortClientService.deleteCoinSort(id);
    }
}
