package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.vo.Coin;
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
@RequestMapping("/consumer")
public class ConsumerCoinController {
    @Resource
    private ICoinClientService iCoinClientService;

    @GetMapping("/coin/{id}")
    public Object getCoin(@PathVariable("id") int id) {
        return iCoinClientService.getCoin(id);
    }

    @GetMapping("/coin")
    public  Object listCoin() {
        return iCoinClientService.listCoin();
    }

    @PostMapping("/coin")
    public Object addCoin(@RequestBody Coin coin) {

        return iCoinClientService.addCoin(coin);
    }

    @PutMapping("/coin")
    public Object updateCoin(@RequestBody Coin coin){
        return iCoinClientService.updateCoin(coin);
    }

    @DeleteMapping("/coin/{id}")
    public Object deleteCoin(@PathVariable("id") int id){
        return iCoinClientService.deleteCoin(id);
    }

}
