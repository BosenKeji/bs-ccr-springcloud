package cn.bosenkeji.controller;

import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
/**
 * @ClassName CoinController
 * @Description 货币
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@RestController
@RequestMapping("/coin")
public class CoinController {

    @Resource
    private CoinService coinService;

    @Resource
    private DiscoveryClient client ;

    @RequestMapping(value="/")
    public Object list() {
        return this.coinService.list() ;
    }

    @RequestMapping(value="/{id}")
    public Object get(@PathVariable("id") int id) {
        return this.coinService.get(id) ;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public Object add(@RequestBody Coin coin) {
        return this.coinService.add(coin) ;
    }

    @RequestMapping(value="/", method = RequestMethod.PUT)
    public Object put(@RequestBody Coin coin) {
        return this.coinService.update(coin) ;
    }

    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") int id) {
        return this.coinService.delete(id) ;
    }



    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
