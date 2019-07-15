package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/userproductcombo")
@Validated
@Api("用户产品套餐api接口")
public class UserProductComboController {

    @Resource
    private IUserProductComboService iUserProductComboService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户产品套餐列表api接口",notes="获取用户产品套餐列表api接口")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<UserProductCombo> list() { return this.iUserProductComboService.list();}

    @ApiOperation(value="获取用户产品套餐详情api接口",notes="获取用户产品套餐详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductCombo get(@PathVariable("id") int id) { return this.iUserProductComboService.get(id);}

    @ApiOperation(value="添加用户产品套餐信息api接口",notes="添加用户产品套餐信息api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody UserProductCombo userProductCombo) { return this.iUserProductComboService.add(userProductCombo);}


    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}
}
