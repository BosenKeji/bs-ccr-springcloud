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
import net.sf.jsqlparser.expression.TimestampValue;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/userproductcombodaybyadmin")
@Validated
@Api(tags = "UserProductCobmoDayByAdmin 用户套餐时长操作相关接口",value="提供用户套餐时长操作相关的 Rest API")
public class UserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户套餐时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListWithPage")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<UserProductComboDayByAdmin> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iUserProductComboDayByAdminService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取用户套餐时长操作详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDayByAdmin get(@PathVariable("id") int id) {
        return this.iUserProductComboDayByAdminService.get(id).orElseThrow(()->new NotFoundException(UserProductComboDayByAdminEnum.NAME));
    }

    @ApiOperation(value="添加用户套餐时长操作信息api接口",httpMethod = "POST",nickname = "addUserProductComboDayByAdmin")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody UserProductComboDay UserProductComboDay,@RequestParam("adminId") int adminId) {
        UserProductComboDay.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        UserProductComboDay.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iUserProductComboDayByAdminService.add(UserProductComboDay,adminId);
    }


    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}

    @ApiOperation(value="通过用户套餐id查询时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListByUserProductComboId")
    @RequestMapping(value = "/listbyuserproductcomboid",method = RequestMethod.GET)
    public List<UserProductComboDayByAdmin> getByUserProductComboId(@RequestParam("userProductComboId") int userProductComboId) {
        return this.iUserProductComboDayByAdminService.getByUserProductComboId(userProductComboId);
    }

    @ApiOperation(value="通过用户电话查询时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListByUserTelWithPage")
    @RequestMapping(value = "/listbyusertel",method = RequestMethod.GET)
    public PageInfo<UserProductComboDayByAdmin> getByUserTel(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize,@RequestParam("userTel") String userTel) {
        return this.iUserProductComboDayByAdminService.getByUserTel(userTel,pageNum,pageSize);
    }
}
