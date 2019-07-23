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
import io.swagger.annotations.ApiParam;
import net.sf.jsqlparser.expression.TimestampValue;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user_product_combo_day_by_admin")
@Validated
@Api(tags = "UserProductComboDayByAdmin 用户套餐时长操作相关接口",value="提供用户套餐时长操作相关的 Rest API")
public class UserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取用户套餐时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListWithPage")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                     @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iUserProductComboDayByAdminService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取用户套餐时长操作详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDayByAdmin get(@PathVariable("id") @Min(1) @ApiParam(value = "用户套餐时长操作ID",required = true,type = "integer",example = "1") int id) {
        return this.iUserProductComboDayByAdminService.get(id).orElseThrow(()->new NotFoundException(UserProductComboDayByAdminEnum.NAME));
    }

    @ApiOperation(value="添加用户套餐时长操作信息api接口",httpMethod = "POST",nickname = "addUserProductComboDayByAdmin")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Optional<Integer> add(@RequestBody @NotNull @ApiParam(value = "用户套餐时长实体",required = true,type = "string") UserProductComboDay UserProductComboDay,
                        @RequestParam("adminId") @NotNull @ApiParam(value = "管理员ID",required = true,type = "integer",example = "1") int adminId) {
        UserProductComboDay.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        UserProductComboDay.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        UserProductComboDay.setStatus(1);
        return this.iUserProductComboDayByAdminService.add(UserProductComboDay,adminId);
    }


    @ApiOperation(value="通过用户套餐id查询时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListByUserProductComboId")
    @RequestMapping(value = "/list_by_user_product_combo_id",method = RequestMethod.GET)
    public List getByUserProductComboId(@RequestParam("userProductComboId")
                                            @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int userProductComboId) {
        return this.iUserProductComboDayByAdminService.getByUserProductComboId(userProductComboId);
    }

    @ApiOperation(value="通过用户电话查询时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListByUserTelWithPage")
    @RequestMapping(value = "/list_by_user_tel",method = RequestMethod.GET)
    public PageInfo getByUserTel(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                 @RequestParam("userTel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String userTel) {
        return this.iUserProductComboDayByAdminService.getByUserTel(userTel,pageNum,pageSize);
    }

    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
