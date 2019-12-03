package cn.bosenkeji.controller;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.interfaces.TradeTypeInterface;
import cn.bosenkeji.service.ITradeOrderClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.SumShellProfitAggregateVo;
import cn.bosenkeji.vo.transaction.SumTradeCostAggregateVo;
import cn.bosenkeji.vo.transaction.TradeOrder;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author CAJR
 * @date 2019/11/6 3:11 下午
 */
@RestController
@RequestMapping("/trade_order")
@Validated
@Api(tags = "TradeOrder 自选货币交易订单接口",value = "自选货币交易订单相关功能 rest接口")
@RefreshScope
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class ConsumerTradeOrderController {
    @Resource
    ITradeOrderClientService iTradeOrderClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取交易订单列表",httpMethod = "GET",nickname = "listTradeOrderByPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1")@Min(1) int pageNum){
        return this.iTradeOrderClientService.listByPage(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "获取单个交易订单信息",httpMethod = "GET",nickname = "getOneTradeOrderByPage")
    @GetMapping("/{id}")
    public TradeOrder getById(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int id){
        return this.iTradeOrderClientService.getById(id);
    }

    @ApiOperation(value = "删除单个交易订单信息",httpMethod = "DELETE",nickname = "deleteTradeOrderById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int id){
        return this.iTradeOrderClientService.delete(id);
    }

    @ApiOperation(value = " 多条件查询 买入日记 方法",httpMethod = "GET",nickname = "searchTradeOrderForBugByCondition")
    @GetMapping("/by_condition_for_buy_logs")
    public OpenSearchPage searchBuyLogsByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") @NotBlank String coinPairChoiceIds,
                                                      @RequestParam(value = "startTime",defaultValue = "0") @ApiParam(value = "开始时间 格式为 1572969600000",example = "1572969600000") Long startTime,
                                                      @RequestParam(value = "endTime",defaultValue = "0") @ApiParam(value = "截止时间 格式为 1573055999999",example = "1573055999999") Long endTime,
                                                      @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                      @RequestParam(value="pageSize",defaultValue="12") int pageSize
    )
    {
        int tradeType = TradeTypeInterface.BUY;
       return this.iTradeOrderClientService.searchTradeOrderByCondition(coinPairChoiceIds,tradeType,startTime,endTime,pageNum,pageSize);
    }

    @ApiOperation(value = " 多条件查询 卖出收益总结 方法",httpMethod = "GET",nickname = "searchTradeOrderForShellByCondition")
    @GetMapping("/by_condition_for_shell_profit")
    public OpenSearchPage searchShellProfitByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds,
                                                   @RequestParam(value = "startTime",defaultValue = "0") @ApiParam(value = "开始时间 格式为 1572969600000",example = "1572969600000") Long startTime,
                                                   @RequestParam(value = "endTime",defaultValue = "0") @ApiParam(value = "截止时间 格式为 1573055999999",example = "1573055999999") Long endTime,
                                                   @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                   @RequestParam(value="pageSize",defaultValue="12") int pageSize
    )
    {
        int tradeType = TradeTypeInterface.SHELL;
        return this.iTradeOrderClientService.searchTradeOrderByCondition(coinPairChoiceIds,tradeType,startTime,endTime,pageNum,pageSize);
    }

    @ApiOperation(value = " 多条件查询 买入 订单 费用 总计 方法",httpMethod = "GET",nickname = "getTradeOrderTotalTradeCostByCondition")
    @GetMapping("/total_trade_cost_by_condition")
    public SumTradeCostAggregateVo getTotalTradeCostByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds
    ) {

        int tradeType = TradeTypeInterface.BUY;
        return this.iTradeOrderClientService.getTotalTradeCostByCondition(coinPairChoiceIds,tradeType);
    }

    @ApiOperation(value = " 多条件查询 订单 收益 总计 方法",httpMethod = "GET",nickname = "getTradeOrderTotalShellProfitByCondition")
    @GetMapping("/total_shell_profit_by_condition")
    public SumShellProfitAggregateVo getTotalShellProfitByCondition(@RequestParam("coinPairChoiceIds") @ApiParam(value = "多个 自选币 id 字符串 不可为空",required = true,type = "string",example = "1,2") String coinPairChoiceIds
    ) {

        return this.iTradeOrderClientService.getTotalShellProfitByCondition(coinPairChoiceIds);
    }

}
