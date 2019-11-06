package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.IUserProductComboDayByAdminService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user_product_combo_day_by_admin")
@Validated
@Api(tags = "UserProductComboDayByAdmin 用户套餐时长操作相关接口",value="提供用户套餐时长操作相关的 Rest API")
@CacheConfig(cacheNames = "ccr:comboDay")
public class UserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;

    @Resource
    private IUserProductComboService iUserProductComboService;

    @Resource
    private DiscoveryClient discoveryClient;



    @ApiOperation(value="获取用户套餐时长操作详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductComboDayByAdmin get(@PathVariable("id") @Min(1) @ApiParam(value = "用户套餐时长操作ID",required = true,type = "integer",example = "1") int id) {
        return this.iUserProductComboDayByAdminService.get(id);
    }

    //@NotNull @ApiParam(value = "用户套餐时长实体",required = true,type = "string")
    //@NotNull @ApiParam(value = "管理员实体",required = true,type = "string")
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true)
            }
    )
    @ApiOperation(value="添加用户套餐时长操作信息api接口",httpMethod = "POST",nickname = "addUserProductComboDayByAdmin")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Result add(
            @RequestBody @NotNull @ApiParam(value = "管理员实体",required = true,type = "string") UserProductComboDayByAdmin userProductComboDayByAdmin) {
        if(iUserProductComboService.checkExistById(userProductComboDayByAdmin.getUserProductComboDay().getUserProductComboId()) != 1) {
            return new Result(0,"用户套餐不存在，增补时长失败！");
        }
        UserProductComboDay userProductComboDay = userProductComboDayByAdmin.getUserProductComboDay();
        userProductComboDay.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDay.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDayByAdmin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDayByAdmin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductComboDayByAdmin.setStatus(1);
        userProductComboDay.setStatus(1);
        return new Result(this.iUserProductComboDayByAdminService.add(userProductComboDay,userProductComboDayByAdmin));
    }




    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
