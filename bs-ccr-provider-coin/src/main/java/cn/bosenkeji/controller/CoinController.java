package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinService;
import cn.bosenkeji.vo.Coin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/coin")
public class CoinController {

    @Resource
    private ICoinService iCoinService;

    @RequestMapping(value="/get/{id}")
    public Object get(@PathVariable("id") int id) {
        return this.iCoinService.get(id) ;
    }
    @RequestMapping(value="/add")
    public Object add(@RequestBody Coin coin) {
        return this.iCoinService.add(coin) ;
    }

    @RequestMapping(value="/list")
    public Object list() {
        return this.iCoinService.list() ;
    }
}
