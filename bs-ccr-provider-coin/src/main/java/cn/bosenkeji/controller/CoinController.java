package cn.bosenkeji.controller;

import cn.bosenkeji.config.ExceptionConfig;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinEnum;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @ClassName CoinController
 * @Description 货币
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@RestController
@RequestMapping("/coin")
@Validated
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
    public Object get( @PathVariable("id")  @Min(value = 1)  int id) {
        return this.coinService.get(id).orElseThrow(()-> new NotFoundException(CoinEnum.NAME)) ;
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public Object add(@RequestBody @NotNull Coin coin) {
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
