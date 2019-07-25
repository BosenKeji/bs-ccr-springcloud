package cn.bosenkeji.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import cn.bosenkeji.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-24 20:13
 */
@RestController
@RequestMapping("/redis")
@Api(value = "Redis 操作工具类api接口",tags = "redis 相关的Rest Api")
public class RedisUtilsController {

    @Autowired
    private RedisTemplate redisTemplate;

    //查询有效时间
    @PostMapping("/getExpire")
    @ApiOperation(value = "获取有效时间api接口",tags = "返回的时间单位是秒",httpMethod = "POST",nickname = "getExpireByKey")
    public long getExpire(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String key) {
        return redisTemplate.getExpire(key);
    }

    //删除一个或多个键
    @PostMapping("/delete")
    @ApiOperation(value = "判断key是否存在api接口",httpMethod = "POST",nickname = "getExpireByKey")
    public Result delete(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String... key) {

        if(key!=null&&key.length>0) {
            if(key.length==1)
                return new Result(redisTemplate.delete(key[0]));
            else {
                return new Result(redisTemplate.delete(CollectionUtils.arrayToList(key)));
            }
        }
        return new Result("0","404");
    }

    //判断键是否存在
    @PostMapping("/hasKey")
    @ApiOperation(value = "判断key是否存在api接口",httpMethod = "POST",nickname = "getExpireByKey")
    public Result hasKey(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String key) {

        try {
            return new Result(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return new Result("0","fail");
        }
    }

    //获取值
    @PostMapping("/get")
    @ApiOperation(value = "获取值api接口",httpMethod = "POST",nickname = "getValueByKey")
    public String get(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String key) {

        return (String)redisTemplate.opsForValue().get(key);
    }

    //设置值
    @ApiOperation(value = "设置值api接口",httpMethod = "POST",nickname = "setValueByKey")
    @PostMapping("/set")
    public Result set(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String key,
                       @RequestParam("value") @NotNull @ApiParam(value = "值",required = true,type = "string",example = "product1") String value) {
        try{
            redisTemplate.opsForValue().set(key,value);
            return new Result<>("1");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result("0","set fail");
        }
    }

    //设置值并指定有效时间
    @ApiOperation(value = "设置值并指定有效时间api接口",httpMethod = "POST",nickname = "setValueByKeyWithTime")
    @PostMapping("/setWithTime")
    public Result setWithTime(@RequestParam("key") @NotNull @ApiParam(value = "键",required = true,type = "string",example = "product1") String key,
                       @RequestParam("value") @NotNull @ApiParam(value = "值",required = true,type = "string",example = "product1") String value,
                       @RequestParam("time") @NotNull @Min(1) @ApiParam(value = "有效时间，单位为秒",required = true,type = "long",example = "3600") long time) {
        try{
            redisTemplate.opsForValue().set(key,value,time,TimeUnit.SECONDS);
            return new Result<>("1");
        }catch (Exception e) {
            e.printStackTrace();
            return new Result("0","set fail");
        }
    }


}
