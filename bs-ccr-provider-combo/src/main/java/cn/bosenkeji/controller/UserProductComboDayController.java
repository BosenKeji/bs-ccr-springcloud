package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.UserProductComboDayEnum;
import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.vo.UserProductComboDay;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/userproductcomboday")
@Validated
@Api("用户套餐时长api接口")
public class UserProductComboDayController {

    @Resource
    private IUserProductComboDayService iUserProductComboDayService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户套餐时长列表api接口",notes="获取用户套餐时长列表api接口",httpMethod = "GET")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<UserProductComboDay> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iUserProductComboDayService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取用户套餐时长详情api接口",notes="获取用户套餐时长详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDay get(@PathVariable("id") @Min(1) int id) { return this.iUserProductComboDayService.get(id).orElseThrow(()->new NotFoundException(UserProductComboDayEnum.NAME));}

    @ApiOperation(value="添加用户套餐时长信息api接口",notes="添加用户套餐时长信息api接口",httpMethod = "POST")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody UserProductComboDay userProductComboDay) {
        userProductComboDay.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDay.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iUserProductComboDayService.add(userProductComboDay);
    }


    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}
}
