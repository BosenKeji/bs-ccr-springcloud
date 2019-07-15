package cn.bosenkeji.controller;

import cn.bosenkeji.service.TradePlatformService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author CAJR
 * @create 2019/7/15 18:01
 */
@RestController
@RequestMapping("/apis")
public class TradePlatformController {

    @Resource
    TradePlatformService tradePlatformService;

    @GetMapping("/")
    public Object list(){
        return this.tradePlatformService.list();
    }
}
