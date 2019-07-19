package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.UserProductComboEnum;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.vo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/userproductcombo")
@Validated
@Api(tags = "UserProductCobmo 用户套餐相关接口",value="提供用户套餐相关的 Rest API")
public class UserProductComboController {

    @Resource
    private IUserProductComboService iUserProductComboService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${pageSize.common}")
    private String pageSizeCommon;

    @ApiOperation(value="获取用户套餐列表api接口",httpMethod = "GET",nickname = "getUserProductComboListWithPage")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<UserProductCombo> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue = "10") int pageSize)
    {
        return this.iUserProductComboService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取用户套餐详情api接口",httpMethod = "GET",nickname = "getUserProductComboInfo")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductCombo get(@PathVariable("id") @Min(1) int id) {
        return this.iUserProductComboService.get(id).orElseThrow(()->new NotFoundException(UserProductComboEnum.NAME));
    }

    @ApiOperation(value="添加用户套餐信息api接口",httpMethod = "POST",nickname = "addUserProductComboInfo")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody @NotNull UserProductCombo userProductCombo) {
        userProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iUserProductComboService.add(userProductCombo);
    }

    @ApiOperation(value="根据用户电话查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserTelWithPage")
    @RequestMapping(value="/listbyusertel",method = RequestMethod.GET)
    public PageInfo<UserProductCombo> listByUserTel(@RequestParam("userTel") String userTel,@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue = "10") int pageSize) {
        //return this.iUserProductComboService.getByUserId(userId);
        return this.iUserProductComboService.selectUserProductComboByUserTel(pageNum,pageSize,userTel);
    }

    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}
}
