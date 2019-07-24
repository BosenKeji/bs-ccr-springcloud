package cn.bosenkeji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-24 20:13
 */
@RestController
@RequestMapping("/redis")
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/get")
    public Object get(@RequestParam("key") String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @PostMapping("/set")
    public boolean set(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") long time) {
        try{
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
