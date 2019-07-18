package cn.bosenkeji.controller;

import cn.bosenkeji.service.IAdminClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName ConsumerAdminController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/consumer")
public class ConsumerAdminController {
    @Resource
    private IAdminClientService iAdminClientService;

    @GetMapping("/admin")
    public  Object listCoin() {
        return iAdminClientService.listByPage();
    }
}
