package controller;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;


/**
 * @ClassName ConsumerAdminController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
public class HomeController {

    @GetMapping(value="/")
    String home(){
        return "Hello Docker World2";
    }

}
