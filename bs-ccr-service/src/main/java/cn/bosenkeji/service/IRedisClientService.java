package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IRedisClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName IProductClientService
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-redis",configuration = FeignClientConfig.class,fallbackFactory = IRedisClientServiceFallbackFactory.class)
public interface IRedisClientService {

   @PostMapping("/redis/get/")
    String get(@RequestParam("key") String key);

   @PostMapping("/redis/set/")
    Result set(@RequestParam("key") String key,@RequestParam("value") String value);

   @PostMapping("/redis/setWithTime/")
    Result setWithTime(@RequestParam("key") String key,@RequestParam("value") String value,@RequestParam("time") long time);
}
