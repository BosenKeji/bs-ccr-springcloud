package cn.bosenkeji.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @ClassName ConsumerAdminController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @RequestMapping(value="/")
    public String home(){
        return "Hello Docker World2";
    }

}
