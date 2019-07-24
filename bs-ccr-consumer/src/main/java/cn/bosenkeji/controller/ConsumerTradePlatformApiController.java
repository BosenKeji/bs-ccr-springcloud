package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApi;
import cn.bosenkeji.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 11:25
 */
@RestController
@RequestMapping("/consumer/trade_platform_api")
@Api(tags = "tradePlatformApi 交易平台api接口",value = "提供交易平台api相关功能 Rest接口")
public class ConsumerTradePlatformApiController {

    @Resource
    ITradePlatformApiClientService iTradePlatformApiClientService;

    @ApiOperation(value = "获取交易平台api单个信息接口",notes = "交易平台api单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformApi")
    @GetMapping("/{tradePlatformId}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("tradePlatformId") @ApiParam(value = "交易平台 id", required = true, type = "integer",example = "1") int tradePlatformId){
        return this.iTradePlatformApiClientService.getOneTradePlatformApi(tradePlatformId);
    }

    @ApiOperation(value = "添加交易平台api单个信息接口",notes = "添加交易平台api单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformApi")
    @PostMapping("/")
    public Optional<Integer> addOneTradePlatformApi(@RequestParam("userId") @ApiParam(value = "user ID", required = true, type = "integer",example = "1") int userId,
                                          @RequestBody @ApiParam(value = "交易平台API实体", required = true)  TradePlatformApi tradePlatformApi){
        return this.iTradePlatformApiClientService.addOneTradePlatformApi(userId,tradePlatformApi);
    }

    @ApiOperation(value = "更新交易平台api接口",notes = "更新交易平台api接口",httpMethod = "PUT",nickname = "updateOneTradePlatformApi")
    @PutMapping("/")
    public Optional<Integer> updateTradePlatformApi(@RequestBody @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        return this.iTradePlatformApiClientService.updateTradePlatform(tradePlatformApi);
    }

    @ApiOperation(value = "删除交易平台api 接口",notes = "删除平台api 接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformApi")
    @DeleteMapping("/{tradePlatformId}")
    public Optional<Integer> deleteOneTradePlatformApi(@PathVariable("tradePlatformId")  @ApiParam(value = "交易平台 id", required = true, type = "integer",example = "1") int tradePlatformId){
        return this.iTradePlatformApiClientService.deleteOneTradePlatform(tradePlatformId);
    }
}
