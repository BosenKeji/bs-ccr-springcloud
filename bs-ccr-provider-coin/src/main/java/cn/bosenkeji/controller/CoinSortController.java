package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinSortEnum;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.vo.CoinSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

/**
 * @Author CAJR
 * @create 2019/7/11 14:23
 */
@RestController
@RequestMapping("/coinsort")
public class CoinSortController {

    @Autowired
    CoinSortService coinSortService;
    @Autowired
    DiscoveryClient discoveryClient;

    @GetMapping("/")
    public Object list(){
        return this.coinSortService.list();
    }

    @GetMapping("/{id}")
    public Object get(@PathVariable @Min(1) int id){
        return this.coinSortService.get(id).orElseThrow(()-> new NotFoundException(CoinSortEnum.NAME));
    }

    @PostMapping("/")
    public boolean add(@RequestBody CoinSort coinSort){
        return this.coinSortService.add(coinSort);
    }

    @PutMapping("/")
    public boolean update(@RequestBody CoinSort coinSort){
        return this.coinSortService.update(coinSort);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinSortService.delete(id);
    }

    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }
}
