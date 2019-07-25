package cn.bosenkeji.controller;

import cn.bosenkeji.service.IRedisClientService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-24 20:24
 */
@RestController
@RequestMapping("/redis")
@Api(value = "Redis 相关api",tags = "redis相关接口")
public class ConsumerRedisController {
    @Resource
    private IRedisClientService iRedisClientService;

    @PostMapping("/get")
    public Object get(@RequestParam("key") String key) {
        return this.iRedisClientService.get(key);
    }

    @PostMapping("/set")
    public Object set(@RequestParam("key") String key,@RequestParam("value") String value) {
        return this.iRedisClientService.set(key, value);
    }

    @PostMapping("/setWithTime")
    public Object setWithTime(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") long time) {
        return this.iRedisClientService.setWithTime(key, value, time);
    }
}
