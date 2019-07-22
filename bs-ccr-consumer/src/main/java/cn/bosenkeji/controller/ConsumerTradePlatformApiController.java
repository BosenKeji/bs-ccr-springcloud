package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.vo.TradePlatformApi;
import cn.bosenkeji.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author CAJR
 * @create 2019/7/22 11:25
 */
@RestController
@RequestMapping("/consumer/tradeplatformapi")
@Api(tags = "tradePlatformApi 交易平台api接口",value = "提供交易平台api相关功能 Rest接口")
public class ConsumerTradePlatformApiController {

    @Resource
    ITradePlatformApiClientService iTradePlatformApiClientService;

    @ApiOperation(value = "获取交易平台api单个信息接口",notes = "交易平台api单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformApi")
    @GetMapping("/{id}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("id") @ApiParam(value = "交易平台API id", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformApiClientService.getOneTradePlatformApi(id);
    }

    @ApiOperation(value = "添加交易平台api单个信息接口",notes = "添加交易平台api单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformApi")
    @PostMapping("/")
    public boolean addOneTradePlatformApi(@RequestParam("user") @ApiParam(value = "user实体", required = true, type = "string") User user,
                                          @RequestParam("tradePlatformApi") @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        return this.iTradePlatformApiClientService.addOneTradePlatformApi(user,tradePlatformApi);
    }

    @ApiOperation(value = "更新交易平台api接口",notes = "更新交易平台api接口",httpMethod = "PUT",nickname = "updateOneTradePlatformApi")
    @PutMapping("/")
    public boolean updateTradePlatformApi(@RequestBody @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        return this.iTradePlatformApiClientService.updateTradePlatform(tradePlatformApi);
    }

    @ApiOperation(value = "删除交易平台api 接口",notes = "删除平台api 接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformApi")
    @DeleteMapping("/{tradePlatformId}")
    public boolean deleteOneTradePlatformApi(@PathVariable("id")  @ApiParam(value = "交易平台 id", required = true, type = "integer",example = "1") int tradePlatformId){
        return this.iTradePlatformApiClientService.deleteOneTradePlatform(tradePlatformId);
    }
}