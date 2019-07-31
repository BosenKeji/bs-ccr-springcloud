package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-31 19:16
 */
@FeignClient(name = "bs-ccr-provider-user-front",configuration = FeignClientConfig.class,fallbackFactory = IUserClientServiceFallbackFactory.class)
public interface IUserClientService {

    @GetMapping("/user/{id}")
    User getOneUser(@PathVariable("id") int id);

    @GetMapping("/user/get_by_username/")
    User getOneUserByUsername(@RequestParam("username") String username);

    @PostMapping("/user/")
    Result addOneUser(@RequestBody User user);

    @PutMapping("/user/")
    Result updateUser(@RequestBody User user);

    @DeleteMapping("/user/{id}")
    Result deleteOneUser(@PathVariable("id") int id);
}
