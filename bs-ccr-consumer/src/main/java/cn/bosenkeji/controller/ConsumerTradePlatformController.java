package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author CAJR
 * @create 2019/7/22 11:24
 */
@RestController
@Validated
@RequestMapping("/trade_platform")
@Api(tags = "tradePlatform 交易平台接口",value = "提供交易平台相关功能 Rest接口")
@RefreshScope
public class ConsumerTradePlatformController {

    @Resource
    ITradePlatformClientService iTradePlatformClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取交易平台分页信息",httpMethod = "GET",nickname = "getListTradePlatformWithPage")
    @GetMapping("/")
    public PageInfo getListTradePlatformWithPage(@RequestParam( value="pageNum",defaultValue="1") @Min(1) int pageNum){
        return this.iTradePlatformClientService.listTradePlatformWithPage(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "获取交易平台单个信息接口",httpMethod = "GET" ,nickname = "getOneTradePlatform")
    @GetMapping("/{id}")
    public TradePlatform getOneTradePlatform(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台ID", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformClientService.getOneTrdPatform(id);
    }

    @ApiOperation(value = "添加交易平台单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatform")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result addOneTradePlatform(@RequestBody @ApiParam(value = "交易平台实体", required = true, type = "string") @NotNull TradePlatform tradePlatform){
        return this.iTradePlatformClientService.addOneTradePlatform(tradePlatform);
    }

    @ApiOperation(value = "更新交易平台接口",httpMethod = "PUT",nickname = "updateTradePlatform")
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result updateTradePlatform(@RequestBody @ApiParam(value = "交易平台实体", required = true, type = "string") @NotNull TradePlatform tradePlatform){
        return this.iTradePlatformClientService.updateTradePlatform(tradePlatform);
    }

    @ApiOperation(value = "删除交易平台接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatform")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result deleteOneTradePlatform(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台ID", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformClientService.deleteOneTradePlatform(id);
    }
}
