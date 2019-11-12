package cn.bosenkeji.controller;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.interfaces.OpenSearchTimeType;
import cn.bosenkeji.service.TradeOrderService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.OrderSearchRequestVo;
import cn.bosenkeji.vo.transaction.SumShellProfitAggregateVo;
import cn.bosenkeji.vo.transaction.SumTradeCostAggregateVo;
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
import java.sql.Timestamp;

/**
 * @author CAJR
 * @date 2019/11/4 3:26 下午
 */
@RestController
@RequestMapping("/trade_order")
@Validated
@Api(tags = "TradeOrder 自选货币交易订单接口",value = "自选货币交易订单相关功能 rest接口")
@CacheConfig(cacheNames = "ccr:tradeOrder")
public class TradeOrderController {

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


    @ApiOperation(value = "删除单个交易订单信息",httpMethod = "DELETE",nickname = "deleteTradeOrderById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int tradeOrderId){
        return new Result<>(this.tradeOrderService.delete(tradeOrderId));
    }

    @ApiOperation(value = " 多条件查询 订单 方法",httpMethod = "GET",nickname = "searchTradeOrderByCondition")
    @GetMapping("/by_condition")
    public OpenSearchPage searchTradeOrderByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds,
                                                      @RequestParam(value = "tradeType",defaultValue = "0") @ApiParam(value = "交易类型 0全部 1买入 2卖出",required = true,type = "integer",example = "1") Integer tradeType,
                                                      @RequestParam(value = "startTime",defaultValue = "0") @ApiParam(value = "开始时间 格式为1673056999999",example = "1673056999999") Long startTime,
                                                      @RequestParam(value = "endTime",defaultValue = "0") @ApiParam(value = "截止时间 格式为1573055999999",example = "1573055999999") Long endTime,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value="pageSize",defaultValue="12") int pageSize
                                              ) {
        OrderSearchRequestVo orderSearchRequestVo = new OrderSearchRequestVo();
        orderSearchRequestVo.setCoinPairChoiceIds(coinPairChoiceIds);
        orderSearchRequestVo.setTradeType(tradeType);
        orderSearchRequestVo.setStartTime(startTime);
        orderSearchRequestVo.setEndTime(endTime);

        return this.tradeOrderService.searchTradeOrderByCondition(orderSearchRequestVo,pageNum,pageSize);
    }

    @ApiOperation(value = " 多条件查询 订单 费用 总计 方法",httpMethod = "GET",nickname = "getTradeOrderTotalTradeCostByCondition")
    @GetMapping("/total_trade_cost_by_condition")
    public SumTradeCostAggregateVo getTotalTradeCostByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds,
                                                                @RequestParam(value = "tradeType",defaultValue = "0") @ApiParam(value = "交易类型 0全部 1买入 2卖出",required = true,type = "integer",example = "1") Integer tradeType
    ) {

        return this.tradeOrderService.searchAggregateTradeOrderSumTradeCostByCondition(coinPairChoiceIds,tradeType);
    }

    @ApiOperation(value = " 多条件查询 订单 收益 总计 方法",httpMethod = "GET",nickname = "getTradeOrderTotalShellProfitByCondition")
    @GetMapping("/total_shell_profit_by_condition")
    public SumShellProfitAggregateVo getTotalShellProfitByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds
    ) {

        return this.tradeOrderService.searchAggregateTradeOrderSumShellProfitByCondition(coinPairChoiceIds);
    }

    @ApiOperation(value = "根据 ID 把 tradeOrder 添加/更新 到openSearch",httpMethod = "PUT",nickname = "pushToOpenSearchById")
    @PutMapping("/to_open_search_by_id")
    public Result<Integer> pushToOpenSearchById(@RequestParam("id") @ApiParam(value = "订单id",required = true,type = "integer",example = "1") @Min(1) int id) {
        return new Result(this.tradeOrderService.updateOpenSearchFromSql(id));
    }
}
