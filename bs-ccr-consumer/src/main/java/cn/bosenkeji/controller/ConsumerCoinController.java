package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.vo.Coin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/coin/get")
    public Object getProduct(int id) {
        return iCoinClientService.getCoin(id);
    }

    @RequestMapping("/coin/list")
    public  Object listProduct() {
        return iCoinClientService.listCoin();
    }

    @RequestMapping("/coin/add")
    public Object addCoin(Coin coin) {
        return iCoinClientService.addCoin(coin);
    }

}
