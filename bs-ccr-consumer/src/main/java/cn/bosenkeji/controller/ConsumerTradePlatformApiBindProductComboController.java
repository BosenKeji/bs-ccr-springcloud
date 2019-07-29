package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-26 18:26
 */
@RestController
@RequestMapping("/consumer/trade_platform_api_bind_product_combo")
@Api(tags = "tradePlatformApiBindProductCombo 交易平台api绑定用户套餐接口",value = "提供交易平台api绑定用户套餐相关功能 Rest接口")
public class ConsumerTradePlatformApiBindProductComboController {

    @Resource
    private ITradePlatformApiBindProductComboClientService iTradePlatformApiBindProductComboClientService;

    @GetMapping("/list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取交易平台api绑定用户套餐列表 api接口"
            ,httpMethod = "GET",nickname = "getTradePlatformApiBindProductComboListByUserId")
    public Object getListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                  @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

       return iTradePlatformApiBindProductComboClientService.getListByUserId(pageNum,pageSize,userId);


    }

    @GetMapping("/get_no_bind_trade_platform_api_list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取用户未绑定的交易平台api列表 api接口"
            ,httpMethod = "GET",nickname = "getNoBindTradePlatformApiListByUserIdWithPage")
    public Object getNoBindTradePlatformApiListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                          @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                          @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return iTradePlatformApiBindProductComboClientService.getNoBindTradePlatformApiListByUserId(pageNum,pageSize,userId);
    }

    @GetMapping("/get_no_bind_user_product_combo_list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取用户未绑定的用户套餐列表 api接口"
            ,httpMethod = "GET",nickname = "getNoBindUserProductComboListByUserIdWithPage")
    public Object getNoBindUserProductComboListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                          @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                          @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return iTradePlatformApiBindProductComboClientService.getNoBindUserProductComboListByUserId(pageNum,pageSize,userId);
    }

    @PostMapping("/")
    @ApiOperation(value = "添加 交易平台api绑定用户套餐 api接口"
            ,httpMethod = "POST",nickname = "addTradePlatformApiBindProductComboList")
    public Object add(@RequestBody @ApiParam(value = "交易谱平台api绑定用户套餐实体",required = true,type = "string") TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

      return iTradePlatformApiBindProductComboClientService.addTradePlatformApiBindProductCombo(tradePlatformApiBindProductCombo);

    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新 交易平添api绑定用户套餐 api接口",httpMethod = "PUT",nickname = "updateTradePlatformApiBindProductCombo")
    public Object updateTradePlatformApiBindProductCombo(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                                                         @RequestParam("tradePlatformApiId") @ApiParam(value = "交易平台api ID",required = true,type = "integer",example = "1") int tradePlatformApiId,
                                                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {
        return iTradePlatformApiBindProductComboClientService.updateTradePlatformApiBindProductCombo(id,tradePlatformApiId,userId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="解除 交易品台绑定用户套餐",httpMethod = "DELETE",nickname = "deleteTradePlatformApiBindProductCombo")
    public Object deleteOneApiBindCombo(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {
        return iTradePlatformApiBindProductComboClientService.deleteTradePlatformApiBindProductCombo(id,userId);
    }
}
