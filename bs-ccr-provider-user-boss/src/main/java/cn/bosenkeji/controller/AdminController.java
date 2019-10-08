package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.AdminEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
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
@Api(tags = "Admin 管理员相关接口",value = "提供管理员相关的 Rest API接口")
@CacheConfig(cacheNames = "ccr:admin")
public class AdminController {

    @Resource
    private AdminService adminService;


    @GetMapping(value="/")
    @ApiOperation(value = "获取管理员列表",httpMethod = "GET",nickname = "getAdminByPage")
    public PageInfo listByPage(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(1) Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(1)  Integer pageSize
    ) {
        return adminService.listByPage(pageNum,pageSize);
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "添加管理员信息",httpMethod = "POST",nickname = "addAdmin")
    public Result add (@RequestBody @NotNull @ApiParam(value = "管理员信息",required = true,type = "Admin") Admin admin) {
        return new Result<>(adminService.add(admin).filter((v)->v>=0)
                .orElseThrow(()->new AddException(AdminEnum.ADMIN_EXIST)));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.ADMIN_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.ADMIN_LIST_IDS_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除指定id的管理员",httpMethod = "DELETE",nickname = "deleteAdminById")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "管理员的id",type = "integer",example = "1") Integer id) {
        return new Result<>(adminService.delete(id).filter((v)->v>=0)
                .orElseThrow(()->new DeleteException(AdminEnum.ADMIN_NOT_FOUND)));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.ADMIN_ID_KEY,key = "#admin.id"),
                    @CacheEvict(value = RedisInterface.ADMIN_LIST_IDS_KEY,allEntries = true)
            }
    )
    @PutMapping(value = "/")
    @ApiOperation(value = "更新指定id的管理员",httpMethod = "PUT",nickname = "updateAdminById")
    public Result update(@RequestBody @NotNull @ApiParam(value = "管理员信息",required = true,type = "Admin") Admin admin) {
        return new Result<>(adminService.update(admin).filter((v)->v>=0)
                .orElseThrow(()->new UpdateException(AdminEnum.ADMIN_NOT_FOUND)));
    }

    @Cacheable(value = RedisInterface.ADMIN_ID_KEY,key = "#id")
    @GetMapping("/{id}")
    @ApiOperation(value = "获取指定id的管理员",httpMethod = "GET",nickname = "getAdminById")
    public Optional<Admin> get(@PathVariable("id") @Min(1) @ApiParam(value = "管理员的id",type = "integer",example = "1") Integer id) {
        return adminService.get(id);
    }

    @GetMapping("/account/{account}")
    @ApiOperation(value = "获取指定Account的管理员",httpMethod = "GET",nickname = "getAdminByAccount")
    public Optional<Admin> selectByAccount(@PathVariable("account") @ApiParam(value = "管理员的Account",type = "integer") String account) {
        return adminService.selectByAccount(account);
    }

    //@Cacheable(value = RedisInterface.ADMIN_LIST_IDS_KEY,key = "#ids")
    @GetMapping("/list_by_ids")
    @ApiOperation(value = "获取多个ID的管理员列表",httpMethod = "GET",nickname = "getAdminListByIds")
    public Map<Integer,Admin> listByIds(@RequestParam("ids") @ApiParam(value = "管理员ID列表",required = true,type = "list",example = "1,2") List<Integer> ids) {
        return adminService.selectByPrimaryKeys(ids);
    }

    @PutMapping("/reset_password")
    public Result resetPassword(@RequestParam("id") @ApiParam(value = "管理员id",type = "integer",required = true) Integer id,
                                @RequestParam("password") @ApiParam(value = "管理员密码",type = "string",required = true) String password) {
        Optional<Integer> optional = adminService.resetPassword(id, password);
        if (optional.get()>0) {
            return new Result<>(optional.get(),"重置密码成功！");
        }
        return new Result<>(optional.get(),"重置密码失败！");
    }

}
