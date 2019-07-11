package cn.bosenkeji.controller;

import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.vo.CoinPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/11 11:45
 */
@RestController
@RequestMapping("/coinpair")
public class CoinPairController {

    @Autowired
    CoinPairService coinPairService;

    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/")
    public Object list(){
        return this.coinPairService.list();
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable("id") int id){
        return this.coinPairService.get(id);
    }

    @PostMapping("/")
    public boolean add(@RequestBody CoinPair coinPair){
        return this.coinPairService.add(coinPair);
    }

    @PutMapping("/")
    public boolean update(@RequestBody CoinPair coinPair){
        return this.coinPairService.update(coinPair);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinPairService.delete(id);
    }

    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }
}
