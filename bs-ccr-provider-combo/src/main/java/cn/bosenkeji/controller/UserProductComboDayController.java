package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.service.IUserProductComboDayService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user_product_combo_day")
@Validated
@Api(tags = "UserProductCobmoDay 用户套餐时长相关接口",value="提供用户套餐时长相关的 Rest API")
public class UserProductComboDayController {

    @Resource
    private IUserProductComboDayService iUserProductComboDayService;

    @Resource
    private DiscoveryClient discoveryClient;

    /*@ApiOperation(value="获取用户套餐时长列表api接口",notes="获取用户套餐时长列表api接口",httpMethod = "GET")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<UserProductComboDay> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iUserProductComboDayService.list(pageNum,pageSize);
    }*/

    @ApiOperation(value="获取用户套餐时长详情api接口",notes="获取用户套餐时长详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDay get(@PathVariable("id") @Min(1) int id) { return this.iUserProductComboDayService.get(id);}

    @ApiOperation(value="添加用户套餐时长信息api接口",notes="添加用户套餐时长信息api接口",httpMethod = "POST")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Result add(@RequestBody UserProductComboDay userProductComboDay) {
        userProductComboDay.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDay.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(this.iUserProductComboDayService.add(userProductComboDay));
    }

    @GetMapping("/list_by_tel")
    @ApiOperation(value = "通过用户电话 获取用户套餐时长列表 接口",httpMethod = "GET")
    public PageInfo listByTel(@RequestParam("tel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String tel, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {

        return this.iUserProductComboDayService.selectByUserTel(tel,pageNum,pageSize);

    }

    @GetMapping("/list_by_user_product_combo_id")
    @ApiOperation(value = "通过用户套餐ID 获取用户套餐时长列表 接口",httpMethod = "GET")
    public PageInfo listByUserProductComboId(@RequestParam("userProductComboId") @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int userProductComboId, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {

        return this.iUserProductComboDayService.selectByUserProductComboId(userProductComboId,pageNum,pageSize);

    }


    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
