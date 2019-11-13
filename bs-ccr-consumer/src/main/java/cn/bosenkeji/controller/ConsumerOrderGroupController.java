package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.TokenUser;
import cn.bosenkeji.service.IOrderGroupClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.OrderGroupOverviewResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author CAJR
 * @date 2019/11/6 3:10 下午
 */
@RestController
@Validated
@RequestMapping("/order_group")
@Api(tags = "orderGroup 自选货币交易订单组接口",value = "自选货币交易订单组相关功能 rest接口")
@RefreshScope
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class ConsumerOrderGroupController {
    @Resource
    IOrderGroupClientService iOrderGroupClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获订单组列表",httpMethod = "GET",nickname = "listOrderGroupByPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") @Min(1) int pageNum,
                               @RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type = "integer",example = "1")@Min(1) int coinPairChoiceId){
        return this.iOrderGroupClientService.listByPageAndCoinPairChoiceId(pageNum, pageSizeCommon,coinPairChoiceId);
    }

    @ApiOperation(value = "获取单个订单组信息",httpMethod = "GET",nickname = "getOneOrderGroupById")
    @GetMapping("/{id}")
    public OrderGroup getById(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int id){
        return this.iOrderGroupClientService.getById(id);
    }

    @ApiOperation(value = "添加单个订单组信息",httpMethod = "POST",nickname = "addOneOrderGroup")
    @PostMapping("/")
    public Result addOneOrderGroup(@RequestBody @ApiParam(value = "交易订单组", required = true, type = "string")@NotNull OrderGroup orderGroup){
        return this.iOrderGroupClientService.addOneOrderGroup(orderGroup);
    }

    @ApiOperation(value = "更新单个交易订单组信息",httpMethod = "PUT",nickname = "updateOrderGroupById")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string")@NotNull OrderGroup orderGroup){
        return this.iOrderGroupClientService.updateOneOrderGroup(orderGroup);
    }

    @ApiOperation(value = "删除单个订单组信息",httpMethod = "DELETE",nickname = "deleteOneOrderGroupById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int id){
        return this.iOrderGroupClientService.deleteOne(id);
    }

    @ApiOperation(value = " 查询 订单组name 方法",httpMethod = "GET",nickname = "searchTradeOrderByCondition")
    @GetMapping("/search_group")
    public Result searchTradeRecordByCondition(@RequestParam(value = "startTime",defaultValue = "0") @ApiParam(value = "开始时间 ",required = true,type = "Long",example = "1") @Min(0) Long startTime,
                                               @RequestParam(value = "endTime",defaultValue = "0") @ApiParam(value = "截止时间 ",required = true,type = "Long",example = "1") @Min(0) Long endTime,
                                               @RequestParam("coinPairChoiceId")@Min(1)@ApiParam(value = "自选币id",required = true,type="integer",example = "1") int coinPairChoiceId){
        return this.iOrderGroupClientService.searchTradeRecordByCondition(startTime, endTime, coinPairChoiceId);
    }
    @ApiOperation(value = "根据 自选币ID 获取交易总览",httpMethod = "GET",nickname = "getCoinPairChoiceTradeOverview")
    @GetMapping("/trade_overview")
    public OrderGroupOverviewResult getCoinPairChoiceTradeOverview(@RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type = "integer",example = "1")@Min(1) int coinPairChoiceId){
        return this.iOrderGroupClientService.getCoinPairChoiceTradeOverview(coinPairChoiceId);
    }
}
