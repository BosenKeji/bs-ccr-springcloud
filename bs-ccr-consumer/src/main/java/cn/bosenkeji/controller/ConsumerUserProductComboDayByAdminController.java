package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboDayByAdminClientService;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 20:33 待开发中……
 */
@RestController
@RequestMapping("/user_product_combo_day_by_admin")
@Api(tags="UserProductComboDayByAdmin 用户时长操作api接口",value = "用户套餐时长操作相关 rest API")
public class ConsumerUserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminClientService iUserProductComboDayByAdminClientService;

    @ApiOperation(value="添加用户套餐时长操作信息api接口",httpMethod = "POST",nickname = "addUserProductComboDayByAdmin")
    @PostMapping("/")
    public Object add(@RequestBody @NotNull @ApiParam(value = "用户套餐时长实体",required = true,type = "string") UserProductComboDay userProductComboDay,
                      @RequestParam("adminId") @NotNull @ApiParam(value = "管理员ID",required = true,type = "integer",example = "1") int adminId)
    {
        return this.iUserProductComboDayByAdminClientService.add(userProductComboDay,adminId);
    }

    /*@ApiOperation(value="获取用户套餐时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListWithPage")
    @GetMapping(value = "/")
    public Object list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                       @RequestParam(value="pageSize",defaultValue="15") int pageSize){
        return this.iUserProductComboDayByAdminClientService.list(pageNum,pageSize);
    }*/

    /*@ApiOperation(value="通过用户套餐id查询时长操作列表api接口 多表联合查询",httpMethod = "GET",
            nickname = "getUserProductComboDayByAdminListByUserProductComboId")
    @GetMapping("/list_by_user_product_combo_id")
    public Object listByUserProductComboId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                           @RequestParam(value="pageSize",defaultValue="15") int pageSize,
            @RequestParam("userProductComboId") @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int userProductComboId) {
        return this.iUserProductComboDayByAdminClientService.listByUserProductComboId(pageNum,pageSize,userProductComboId);
    }*/

    /*@ApiOperation(value="通过用户电话查询时长操作列表api接口 多表联合查询",httpMethod = "GET",nickname = "getUserProductComboDayByAdminListByUserTelWithPage")
    @GetMapping("/list_by_user_tel")
    public Object listByUserTel(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                @RequestParam("userTel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String userTel) {
        return this.iUserProductComboDayByAdminClientService.listByUserTel(pageNum,pageSize,userTel);
    }*/
}
