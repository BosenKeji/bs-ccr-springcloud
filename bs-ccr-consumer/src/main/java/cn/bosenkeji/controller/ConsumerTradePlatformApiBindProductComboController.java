package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.service.impl.CustomUserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-26 18:26
 */
@RestController
@RequestMapping("/trade_platform_api_bind_product_combo")
@Api(tags = "tradePlatformApiBindProductCombo 交易平台api绑定用户套餐接口",value = "提供交易平台api绑定用户套餐相关功能 Rest接口")
@PreAuthorize("hasAnyAuthority('USER')")
public class ConsumerTradePlatformApiBindProductComboController {

    @Resource
    private ITradePlatformApiBindProductComboClientService iTradePlatformApiBindProductComboClientService;

    @GetMapping("/list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取交易平台api绑定用户套餐列表 api接口"
            ,httpMethod = "GET",nickname = "getTradePlatformApiBindProductComboListByUserId")
    public Object getListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                  @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        int currentUserId=this.getCurrentUser().getId();
       return iTradePlatformApiBindProductComboClientService.getListByUserId(pageNum,pageSize,currentUserId);


    }

    @GetMapping("/get_no_bind_trade_platform_api_list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取用户未绑定的交易平台api列表 api接口"
            ,httpMethod = "GET",nickname = "getNoBindTradePlatformApiListByUserIdWithPage")
    public Object getNoBindTradePlatformApiListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                          @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                                          @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        int currentUserId=this.getCurrentUser().getId();
        return iTradePlatformApiBindProductComboClientService.getNoBindTradePlatformApiListByUserId(pageNum,pageSize,currentUserId);
    }


    @PutMapping("/{id}")
    @ApiOperation(value = "更新 交易平添api绑定用户套餐 api接口",httpMethod = "PUT",nickname = "updateTradePlatformApiBindProductCombo")
    public Object updateTradePlatformApiBindProductCombo(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                                                         @RequestParam("tradePlatformApiId") @ApiParam(value = "交易平台api ID",required = true,type = "integer",example = "1") int tradePlatformApiId,
                                                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {
        int currentUserId=this.getCurrentUser().getId();
        return iTradePlatformApiBindProductComboClientService.updateTradePlatformApiBindProductCombo(id,tradePlatformApiId,currentUserId);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="解除 交易品台绑定用户套餐",httpMethod = "DELETE",nickname = "deleteTradePlatformApiBindProductCombo")
    public Object deleteOneApiBindCombo(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {
        int currentUserId=this.getCurrentUser().getId();
        return iTradePlatformApiBindProductComboClientService.deleteTradePlatformApiBindProductCombo(id,currentUserId);
    }

    public CustomUserDetailsImpl getCurrentUser() {
        CustomUserDetailsImpl principal = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}
