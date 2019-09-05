package cn.bosenkeji.controller;

import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.service.impl.CustomAdminDetailsImpl;
import cn.bosenkeji.service.impl.CustomUserDetailsImpl;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
@Validated
@Api
public class ConsumerAdminController {
    @Resource
    private IAdminClientService adminClientService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value="/")
    @ApiOperation(value = "获取管理员列表",httpMethod = "GET",nickname = "getAdminByPage")
    public PageInfo listByPage(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(1)  Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(1)  Integer pageSize
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return adminClientService.listByPage(pageNum,pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{id}")
    @ApiOperation(value = "获取指定id的管理员",httpMethod = "GET",nickname = "getAdminById")
    public Optional<Admin> get(@PathVariable("id") @Min(1) @ApiParam(value = "管理员的id",type = "integer",example = "1") Integer id) {
        return adminClientService.get(id);
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "添加管理员信息",httpMethod = "POST",nickname = "addAdmin")
    public Result add (@RequestBody @NotNull @ApiParam(value = "管理员信息",required = true,type = "Admin") Admin admin) {
        return adminClientService.add(admin);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping(value = "/")
    @ApiOperation(value = "更新指定id的管理员",httpMethod = "PUT",nickname = "updateAdminById")
    public Result update(@RequestBody @NotNull @ApiParam(value = "管理员信息",required = true,type = "Admin") Admin admin) {
        return adminClientService.update(admin);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除指定id的管理员",httpMethod = "DELETE",nickname = "deleteAdminById")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "管理员的id",type = "Integer",example = "1") Integer id) {
        return adminClientService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/reset_password")
    public Result resetPassword(@RequestParam("id") int id,@RequestParam(value = "password",required = false,defaultValue = "888888") String password) {
        return adminClientService.resetPassword(id,password);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "获取当前登录管理员接口",httpMethod = "GET",nickname = "getCurrentAdmin")
    @GetMapping("/current_admin")
    public CustomAdminDetailsImpl getCurrentAdmin() {
        CustomAdminDetailsImpl principal = (CustomAdminDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}
