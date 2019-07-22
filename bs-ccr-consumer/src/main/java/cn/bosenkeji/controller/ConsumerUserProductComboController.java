package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.vo.UserProductCombo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 19:47
 */
@RestController
@RequestMapping("/consumer")
@Api(tags = "用户套餐api接口",value = "提供用户套餐相关的 rest API")
public class ConsumerUserProductComboController {

    @Resource
    private IUserProductComboClientService iUserProductComboClientService;

    @ApiOperation(value = "新增用户套餐api接口",httpMethod = "POST")
    @PostMapping("/userproductcombo")
    public Object add(@RequestBody UserProductCombo userProductCombo) { return this.iUserProductComboClientService.add(userProductCombo);}

    @ApiOperation(value = "通过电话号码查询用户套餐列表api接口",notes="不传分页参数默认查询前十五条记录",httpMethod = "GET")
    @GetMapping("/userproductcombo/listbyusertel")
    public Object listByUserTel(@RequestParam("userTel") String userTel,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize) { return this.iUserProductComboClientService.listByUserTel(userTel,pageNum,pageSize);}

}
