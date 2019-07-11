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
@RequestMapping("/consumer/coin")
public class ConsumerCoinController {
    @Resource
    private ICoinClientService iCoinClientService;

    @GetMapping("/{id}")
    public Object getProduct(@PathVariable int id) {
        return iCoinClientService.getCoin(id);
    }

    @GetMapping("/")
    public  Object listProduct() {
        return iCoinClientService.listCoin();
    }

    @PostMapping("/")
    public Object addCoin(Coin coin) {

        return iCoinClientService.addCoin(coin);
    }

}
