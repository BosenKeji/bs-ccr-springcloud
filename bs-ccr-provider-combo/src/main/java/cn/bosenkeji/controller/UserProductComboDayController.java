package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.vo.UserProductComboDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/userproductcomboday")
@Validated
@Api("用户产品套餐时长api接口")
public class UserProductComboDayController {

    @Resource
    private IUserProductComboDayService iUserProductComboDayService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户产品套餐时长列表api接口",notes="获取用户产品套餐时长列表api接口")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<UserProductComboDay> list() { return this.iUserProductComboDayService.list();}

    @ApiOperation(value="获取用户产品套餐时长详情api接口",notes="获取用户产品套餐时长详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDay get(@PathVariable("id") @Min(1) int id) { return this.iUserProductComboDayService.get(id);}

    @ApiOperation(value="添加用户产品套餐时长信息api接口",notes="添加用户产品套餐时长信息api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody UserProductComboDay UserProductComboDay) { return this.iUserProductComboDayService.add(UserProductComboDay);}

    @ApiOperation(value="更新用户产品套餐时长信息api接口",notes="更新用户产品套餐时长信息api接口")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody UserProductComboDay UserProductComboDay) { return this.iUserProductComboDayService.update(UserProductComboDay);}

    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}
}
