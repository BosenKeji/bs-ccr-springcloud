package cn.bosenkeji.controller;

import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName ConsumerAdminController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/admin")
public class ConsumerAdminController {
    @Resource
    private IAdminClientService adminClientService;

    @GetMapping("/")
    public PageInfo listByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
            ) {
        return adminClientService.listByPage(pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public Optional<Admin> get(@PathVariable("id") Integer id) {
        return adminClientService.get(id);
    }

    @PostMapping("/")
    public Result add(Admin admin) {
        return adminClientService.add(admin);
    }

    @PutMapping("/")
    public Result update(Admin admin) {
        return adminClientService.update(admin);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return adminClientService.delete(id);
    }
}
