/**
 * FileName: TestController
 * Author: xivin
 * Date: 2019-08-20 17:50
 * Description:
 */
package cn.bosenkeji.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    @Cacheable(value = "test",key = "#id")
    @GetMapping("/test")
    public String test(int id) {
        return "test"+id;
    }


    @GetMapping("/testTemplate")
    public String testTemplate(int id) {
        try{
            redisTemplate.opsForValue().set("template","test template!!!");
            return "success"+redisTemplate.opsForValue().get("template");
        }catch (Exception e) {
            e.printStackTrace();
            return "fail"+e.getLocalizedMessage();
        }

    }


}
