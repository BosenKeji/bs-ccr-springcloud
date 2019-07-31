package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.AdminEnum;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Resource
    private AdminService adminService;


    @GetMapping(value="/")
    public PageInfo listByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return adminService.listByPage(pageNum,pageSize);
    }

    @PostMapping(value = "/")
    public Result add (@RequestBody Admin admin) {
        return new Result(adminService.add(admin).filter((v)->v>=0)
                .orElseThrow(()->new AddException(AdminEnum.ADMIN_EXIST)));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return new Result(adminService.delete(id).filter((v)->v>=0)
                .orElseThrow(()->new DeleteException(AdminEnum.ADMIN_NOT_FOUND)));
    }

    @PutMapping(value = "/")
    public Result update(@RequestBody Admin admin) {
        return new Result(adminService.update(admin).filter((v)->v>=0)
                .orElseThrow(()->new UpdateException(AdminEnum.ADMIN_NOT_FOUND)));
    }

    @GetMapping("/{id}")
    public Optional<Admin> get(@PathVariable Integer id) {
        return adminService.get(id);
    }

}
