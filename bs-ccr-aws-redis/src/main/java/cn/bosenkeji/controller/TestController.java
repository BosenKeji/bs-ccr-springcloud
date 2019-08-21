/**
 * FileName: TestController
 * Author: xivin
 * Date: 2019-08-20 17:50
 * Description:
 */
package cn.bosenkeji.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Cacheable(value = "test",key = "#id")
    @GetMapping("/test")
    public String test(int id) {
        return "test"+id;
    }
}
