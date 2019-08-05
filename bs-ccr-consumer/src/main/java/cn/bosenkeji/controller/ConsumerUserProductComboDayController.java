package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboDayClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 20:33 待开发中……
 */
@RestController
@RequestMapping("/user_product_combo_day")
@Api(tags="UserProductComboDay 用户时长接口",value = "用户套餐时长相关 rest API")
public class ConsumerUserProductComboDayController {

    @Resource
    private IUserProductComboDayClientService iUserProductComboDayClientService;

    @GetMapping("/list_by_tel")
    @ApiOperation(value = "通过用户电话 获取用户套餐时长列表 接口",httpMethod = "GET")
    public Object listByTel(@RequestParam("tel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String tel, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {

        return this.iUserProductComboDayClientService.listByTel(tel,pageNum,pageSize);

    }

    @GetMapping("/list_by_user_product_combo_id")
    @ApiOperation(value = "通过用户套餐ID 获取用户套餐时长列表 接口",httpMethod = "GET")
    public Object listByUserProductComboId(@RequestParam("userProductComboId") @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int userProductComboId, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize) {

        return this.iUserProductComboDayClientService.listByUserProductComboId(userProductComboId,pageNum,pageSize);

    }


}