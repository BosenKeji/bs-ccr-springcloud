package cn.bosenkeji.controller;

import cn.bosenkeji.service.ITradeOrderClientService;
import cn.bosenkeji.util.Result;
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

    @ApiOperation(value = "添加单个交易订单信息",httpMethod = "POST",nickname = "addOneTradeOrder")
    @PostMapping("/")
    public Result addOneOrderGroup(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string") @NotNull TradeOrder tradeOrder){
        return this.iTradeOrderClientService.addOneOrderGroup(tradeOrder);
    }


    @ApiOperation(value = "删除单个交易订单信息",httpMethod = "DELETE",nickname = "deleteTradeOrderById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单id", required = true, type = "integer",example = "1") int id){
        return this.iTradeOrderClientService.delete(id);
    }
}
