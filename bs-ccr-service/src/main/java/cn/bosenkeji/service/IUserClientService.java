package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-31 19:16
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = IUserClientServiceFallbackFactory.class)
public interface IUserClientService {

    @GetMapping("/user/{id}")
    User getOneUser(@PathVariable("id") int id);

    @GetMapping("/user/get_by_username/")
    User getOneUserByUsername(@RequestParam("username") String username);

    @GetMapping("/user/get_by_tel/")
    User getOneUserByTel(@RequestParam("tel") String tel);

    @PostMapping("/user/")
    Result addOneUser(@RequestBody User user);

    @PutMapping("/user/")
    Result updateUser(@RequestBody User user);

    @DeleteMapping("/user/{id}")
    Result deleteOneUser(@PathVariable("id") int id);

    @PutMapping("/user/update_password/{id}")
    Result updateUserPassword(@PathVariable("id") int id,@RequestParam("password") String password);

    @PutMapping("/user/update_tel/{id}")
    Result updateUserTel(@PathVariable("id") int id,@RequestParam("tel") String tel);

    @PutMapping("/user/update_username/{id}")
    Result updateUserUsername(@PathVariable("id") int id,@RequestParam("username") String username);

    @PutMapping("/user/update_binding")
    Result updateBinding(@RequestParam("id") int id,@RequestParam("isBinding") int isBinding);

    @PutMapping("/user/update_password_by_tel")
    Result updatePasswordByTel(@RequestParam("tel") String tel,@RequestParam("password") String password);

    @GetMapping("/user/list_by_ids")
    public Map<Integer, User> listByIds(@RequestParam("ids") List<Integer> ids);

    @GetMapping("/user/")
    public PageInfo listByPage(@RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize);


}
