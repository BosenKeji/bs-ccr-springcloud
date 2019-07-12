package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairCoinEnum;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.vo.CoinPairCoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @Author CAJR
 * @create 2019/7/11 12:14
 */
@RestController
@RequestMapping("/coinpaircoin")
public class CoinPairCoinController {

    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    CoinPairCoinService coinPairCoinService;

    @GetMapping("/")
    public Object list(){
        return this.coinPairCoinService.list();
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable("id") @Min(1) int id){
        return this.coinPairCoinService.get(id).orElseThrow(()-> new NotFoundException(CoinPairCoinEnum.NAME));
    }

    @PostMapping("/")
    public boolean add(@RequestBody CoinPairCoin coinPairCoin){
        return this.coinPairCoinService.add(coinPairCoin);
    }

    @PutMapping("/")
    public boolean update(@RequestBody CoinPairCoin coinPairCoin){
        return this.coinPairCoinService.update(coinPairCoin);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinPairCoinService.delete(id);
    }

    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }

}
