package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.annotation.TokenUser;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

/**
 * @Author CAJR
 * @create 2019/7/22 11:25
 */
@RestController
@RequestMapping("/trade_platform_api")
@Api(tags = "tradePlatformApi 交易平台api接口",value = "提供交易平台api相关功能 Rest接口")
@RefreshScope
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class ConsumerTradePlatformApiController {

    @Resource
    ITradePlatformApiClientService iTradePlatformApiClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "根据用户id获取交易平台api列表信息接口",httpMethod = "GET",nickname = "getListTradePlatformApiByUserId")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam( value="pageNum",defaultValue="1") int pageNum, @ApiIgnore @TokenUser int userId){
        return this.iTradePlatformApiClientService.listTradePlatformApi(pageNum, pageSizeCommon, userId);
    }

    @ApiOperation(value = "获取交易平台api单个信息接口",notes = "交易平台api单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformApi")
    @GetMapping("/{id}")
    public TradePlatformApi getOneTradePlatformApi(@PathVariable("id") @ApiParam(value = "交易平台api id", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformApiClientService.getOneTradePlatformApi(id);
    }


    @ApiOperation(value = "添加交易平台api单个信息接口",notes = "添加交易平台api单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformApi")
    @PostMapping("/")
    public Result addOneTradePlatformApi(@RequestBody @ApiParam(value = "交易平台API实体", required = true, type = "string")  TradePlatformApi tradePlatformApi,@ApiIgnore @TokenUser int userId){
        tradePlatformApi.setUserId(userId);
        return this.iTradePlatformApiClientService.addOneTradePlatformApi(tradePlatformApi);
    }

    @ApiOperation(value = "更新交易平台api接口",notes = "更新交易平台api接口",httpMethod = "PUT",nickname = "updateOneTradePlatformApi")
    @PutMapping("/")
    public Result updateTradePlatformApi(@RequestBody @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi,@ApiIgnore @TokenUser int userId){
        tradePlatformApi.setUserId(userId);
        return this.iTradePlatformApiClientService.updateTradePlatformApi(tradePlatformApi);
    }

    @ApiOperation(value = "删除交易平台api 接口",notes = "删除平台api 接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformApi")
    @DeleteMapping("/{id}")
    public Result deleteOneTradePlatformApi(@PathVariable("id")  @ApiParam(value = "交易平台api id", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformApiClientService.deleteOneTradePlatformApi(id);
    }
}
