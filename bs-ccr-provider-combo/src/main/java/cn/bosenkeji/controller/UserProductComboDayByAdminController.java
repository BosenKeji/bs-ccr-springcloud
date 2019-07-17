package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.UserProductComboDayByAdminEnum;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/userproductcombodaybyadmin")
@Validated
@Api("用户套餐时长操作api接口")
public class UserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户套餐时长操作列表api接口",notes="获取用户套餐时长操作列表api接口")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<UserProductComboDayByAdmin> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iUserProductComboDayByAdminService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取用户套餐时长操作详情api接口",notes="获取用户套餐时长操作详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDayByAdmin get(@PathVariable("id") int id) {
        return this.iUserProductComboDayByAdminService.get(id).orElseThrow(()->new NotFoundException(UserProductComboDayByAdminEnum.NAME));
    }

    @ApiOperation(value="添加用户套餐时长操作信息api接口",notes="添加用户套餐时长操作信息api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody UserProductComboDay UserProductComboDay,@RequestParam("adminId") int adminId) { return this.iUserProductComboDayByAdminService.add(UserProductComboDay,adminId);}

    @ApiOperation(value="更新用户套餐时长api接口",notes="更新用户套餐时长api接口")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody UserProductComboDayByAdmin UserProductComboDayByAdmin) { return this.iUserProductComboDayByAdminService.update(UserProductComboDayByAdmin);}

    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}
}
