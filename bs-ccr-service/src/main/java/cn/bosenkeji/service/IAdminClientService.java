package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IAdminClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName IAdminClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-user",configuration = FeignClientConfig.class
        ,fallbackFactory = IAdminClientServiceFallbackFactory.class
)
public interface IAdminClientService {


    @GetMapping("/admin/")
    PageInfo listByPage(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize);

    @GetMapping("/admin/{id}")
    Optional<Admin> get(@PathVariable("id") int id);

    @PostMapping("/admin/")
    Result add(@RequestBody Admin admin);

    @PutMapping("/admin/")
    Result update(Admin admin);

    @DeleteMapping("/admin/{id}")
    Result delete(@PathVariable("id") int id);

    @GetMapping("/admin/account/{account}")
    public Optional<Admin> selectByAccount(@PathVariable("account")  String account);
}
