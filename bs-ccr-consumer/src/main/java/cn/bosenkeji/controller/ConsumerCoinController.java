package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.vo.Coin;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName ConsumerCoinController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/consumer/coin")
public class ConsumerCoinController {
    @Resource
    private ICoinClientService iCoinClientService;

    @GetMapping("/{id}")
    public Coin getCoin(@PathVariable("id") int id) {
        return iCoinClientService.getCoin(id);
    }

    @GetMapping("/")
    public PageInfo listCoin(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                             @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinClientService.listCoin(pageNum, pageSizeCommon);
    }

    @PostMapping("/")
    public boolean addCoin(@RequestBody Coin coin) {

        return iCoinClientService.addCoin(coin);
    }

    @PutMapping("/")
    public boolean updateCoin(@RequestBody Coin coin){
        return iCoinClientService.updateCoin(coin);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCoin(@PathVariable("id") int id){
        return iCoinClientService.deleteCoin(id);
    }

}
