package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.vo.CoinSort;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/consumer/coinsort")
public class ConsumerCoinSortController {

    @Autowired
    ICoinSortClientService iCoinSortClientService;

    @GetMapping("/{id}")
    public CoinSort getCoinSort(@PathVariable("id") int id) {
        return iCoinSortClientService.getCoinSort(id);
    }

    @GetMapping("/")
    public PageInfo listProduct(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinSortClientService.listCoinSort(pageNum, pageSizeCommon);
    }

    @PostMapping("/")
    public Object addCoin(@RequestBody CoinSort coinSort) {

        return iCoinSortClientService.addCoinSort(coinSort);
    }

    @PutMapping("/")
    public Object updateCoin(@RequestBody CoinSort coinSort){
        return iCoinSortClientService.updateCoinSort(coinSort);
    }

    @DeleteMapping("/{id}")
    public Object deleteCoinSort(@PathVariable("id") int id){
        return iCoinSortClientService.deleteCoinSort(id);
    }
}
