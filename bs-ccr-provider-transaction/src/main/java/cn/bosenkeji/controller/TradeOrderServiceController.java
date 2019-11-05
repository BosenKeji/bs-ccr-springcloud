package cn.bosenkeji.controller;

import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.TradeOrder;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

/**
 * @author CAJR
 * @date 2019/11/4 3:26 下午
 */
@RestController
@RequestMapping("/trade_order")
@Validated
@Api(tags = "TradeOrder 自选货币交易订单接口",value = "自选货币交易订单相关功能 rest接口")
@CacheConfig(cacheNames = "ccr:tradeOrder")
public class TradeOrderServiceController {

    @Resource
    TradeOrderService tradeOrderService;


    @ApiOperation(value = "获取交易订单列表",httpMethod = "GET",nickname = "listTradeOrderByPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.tradeOrderService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个交易订单信息",httpMethod = "GET",nickname = "getOneTradeOrderByPage")
    @GetMapping("/{id}")
    public TradeOrder getById(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int tradeOrderId){
        return this.tradeOrderService.getById(tradeOrderId);
    }

    @ApiOperation(value = "添加单个交易订单信息",httpMethod = "POST",nickname = "addOneTradeOrder")
    @PostMapping("/")
    public Result addOneOrderGroup(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string") TradeOrder tradeOrder){
        return new Result<>(this.tradeOrderService.add(tradeOrder));
    }

    @ApiOperation(value = "更新单个交易订单信息",httpMethod = "PUT",nickname = "updateTradeOrderById")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string") TradeOrder tradeOrder){
        return new Result<>(this.tradeOrderService.update(tradeOrder));
    }

    @ApiOperation(value = "删除单个交易订单信息",httpMethod = "DELETE",nickname = "deleteTradeOrderById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int tradeOrderId){
        return new Result<>(this.tradeOrderService.delete(tradeOrderId));
    }
}
