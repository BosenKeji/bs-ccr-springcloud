package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformCoinPairClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author CAJR
 * @create 2019/7/22 11:26
 */
@RestController
@RequestMapping("/consumer/trade_platform_coinpair")
@Api(tags = "tradePlatformCoinPair 交易平台货币对接口",value = "提供交易平台货币对相关功能 Rest接口")
public class ConsumerTradePlatformCoinPairController {
    @Resource
    ITradePlatformCoinPairClientService iTradePlatformCoinPairClientService;

    @ApiOperation(value = "获取平台货币对列表接口",httpMethod = "GET",nickname = "getTradePlatformCoinPairWithPage")
    @GetMapping("/")
    public PageInfo getListTradePlatformCoinPairWithPage(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.iTradePlatformCoinPairClientService.listTradePlatformCoinPairs(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "获取平台货币对单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformCoinPair")
    @GetMapping("/{id}")
    public TradePlatformCoinPair getOneTradePlatformCoinPair(@PathVariable("id") @ApiParam(value = "交易平台货币对ID", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformCoinPairClientService.getOneTradePlatformCoinPair(id);
    }

    @ApiOperation(value = "添加平台货币对单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformCoinPair")
    @PostMapping("/")
    public boolean addOneTradePlatformCoinPair(@RequestBody @ApiParam(value = "交易平台货币对实体", required = true, type = "string") TradePlatformCoinPair tradePlatformCoinPair){
        return this.iTradePlatformCoinPairClientService.addOneTradePlatformCoinPair(tradePlatformCoinPair);
    }

    @ApiOperation(value = "更新单个平台货币对接口",httpMethod = "PUT",nickname = "updateTradePlatformCoinPair")
    @PutMapping("/")
    public boolean updateTradePlatformCoinPair(@RequestBody @ApiParam(value = "交易平台货币对实体", required = true, type = "string") TradePlatformCoinPair tradePlatformCoinPair){
        return this.iTradePlatformCoinPairClientService.updateTradePlatformCoinPair(tradePlatformCoinPair);
    }

    @ApiOperation(value = "删除单个平台货币对接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformCoinPair")
    @DeleteMapping("/{id}")
    public boolean deleteOneTradePlatformCoinPair(@PathVariable("id") @ApiParam(value = "交易平台货币对ID", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformCoinPairClientService.deleteOneTradePlatformCoinPair(id);
    }
}
