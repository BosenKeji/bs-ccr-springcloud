package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradePlatformCoinPairClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
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
 * @create 2019/7/22 11:26
 */
@RestController
@Validated
@RequestMapping("/trade_platform_coin_pair")
@Api(tags = "tradePlatformCoinPair 交易平台货币对接口",value = "提供交易平台货币对相关功能 Rest接口")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@RefreshScope
public class ConsumerTradePlatformCoinPairController {
    @Resource
    ITradePlatformCoinPairClientService iTradePlatformCoinPairClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取平台货币对列表接口",httpMethod = "GET",nickname = "getTradePlatformCoinPairWithPage")
    @GetMapping("/")
    public PageInfo getListTradePlatformCoinPairWithPage(@RequestParam( value="pageNum",defaultValue="1") @Min(1) int pageNum){
        return this.iTradePlatformCoinPairClientService.listTradePlatformCoinPairs(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "获取平台货币对单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformCoinPair")
    @GetMapping("/{id}")
    public TradePlatformCoinPair getOneTradePlatformCoinPair(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台货币对ID", required = true, type = "integer",example = "1") int id){
        return this.iTradePlatformCoinPairClientService.getOneTradePlatformCoinPair(id);
    }

    @ApiOperation(value = "添加平台货币对单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformCoinPair")
    @PostMapping("/")
    public Result addOneTradePlatformCoinPair(@RequestParam("coinPairName") @ApiParam(value = "货币名字",required = true,type = "string") @NotNull String coinPairName,
                                              @RequestParam("isPopular") @ApiParam(value = "是否流行",required = true,type = "int",example = "1") @Min(0) int isPopular,
                                              @RequestParam("isOfficialSet") @ApiParam(value = "是否官方推荐",required = true,type = "int",example = "1") @Min(0) int isOfficialSet,
                                              @RequestParam("tradePlatformName") @ApiParam(value = "交易平台名字",required = true,type = "string") @NotNull String tradePlatformName){
        CoinPair coinPair = new CoinPair();
        coinPair.setName(coinPairName);
        coinPair.setIsOfficialSet(isPopular);
        coinPair.setIsOfficialSet(isOfficialSet);
        return this.iTradePlatformCoinPairClientService.addOneTradePlatformCoinPair(coinPair, tradePlatformName);
    }

    @ApiOperation(value = "更新单个平台货币对接口",httpMethod = "PUT",nickname = "updateTradePlatformCoinPair")
    @PutMapping("/")
    public Result updateTradePlatformCoinPair(@RequestBody @ApiParam(value = "交易平台货币对实体", required = true, type = "string") @NotNull TradePlatformCoinPair tradePlatformCoinPair){
        return this.iTradePlatformCoinPairClientService.updateTradePlatformCoinPair(tradePlatformCoinPair);
    }

    @ApiOperation(value = "删除单个平台货币对接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformCoinPair")
    @DeleteMapping("/")
    public Result deleteOneTradePlatformCoinPair(@RequestParam("tradePlatformId") @Min(1) @ApiParam(value = "交易平台ID", required = true, type = "integer",example = "1") int tradePlatformId,
                                                  @RequestParam("coinPairId") @Min(1) @ApiParam(value = "交易平台货币对ID", required = true, type = "integer",example = "1") int coinPairId){
        return this.iTradePlatformCoinPairClientService.deleteOneTradePlatformCoinPair(tradePlatformId, coinPairId);
    }
}
