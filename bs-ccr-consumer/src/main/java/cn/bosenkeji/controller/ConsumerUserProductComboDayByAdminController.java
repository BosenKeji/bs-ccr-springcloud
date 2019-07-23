package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboDayByAdminClientService;
import cn.bosenkeji.vo.UserProductComboDay;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 20:33 待开发中……
 */
@RestController
@RequestMapping("/consumer/user_product_combo_day_by_admin")
@Api(tags="UserProductComboDayByAdmin 用户时长操作api接口",value = "用户套餐时长操作相关 rest API")
public class ConsumerUserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminClientService iUserProductComboDayByAdminClientService;

    @ApiOperation(value = "新增用户时长api接口",httpMethod = "POST")
    @PostMapping("/")
    public Object add(@RequestBody UserProductComboDay userProductComboDay, @RequestParam("adminId") int adminId)
    {
        return this.iUserProductComboDayByAdminClientService.add(userProductComboDay,adminId);
    }

    @GetMapping(value = "/")
    public Object list(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                       @RequestParam(value = "pageSize",defaultValue = "15") int pageSize){
        return this.iUserProductComboDayByAdminClientService.list(pageNum,pageSize);}

    @GetMapping("/list_by_user_product_combo_id")
    public Object listByUserProductComboId(int userProductComboId) {
        return this.iUserProductComboDayByAdminClientService.listByUserProductComboId(userProductComboId);
    }

    @GetMapping("/list_by_user_tel")
    public Object listByUserTel(int pageNum,int pageSize,@RequestParam("userTel") String userTel) {
        return this.iUserProductComboDayByAdminClientService.listByUserTel(pageNum,pageSize,userTel);
    }
}
