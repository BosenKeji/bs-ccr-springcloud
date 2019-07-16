package cn.bosenkeji.controller;

import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @RequestMapping(value="/")
    public PageInfo listByPage() {
        return  adminService.listByPage(0,pageSizeCommon);
    }
}
