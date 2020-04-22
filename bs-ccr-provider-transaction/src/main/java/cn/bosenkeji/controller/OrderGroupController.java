package cn.bosenkeji.controller;

import cn.bosenkeji.OpenSearchPage;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.OrderGroupService;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.OrderGroup;
import cn.bosenkeji.vo.transaction.OrderGroupOverviewResult;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.Optional;

/**
 * @author CAJR
 * @date 2019/11/4 3:34 下午
 */
@RestController
@Validated
@RequestMapping("/order_group")
@Api(tags = "orderGroup 自选货币交易订单组接口",value = "自选货币交易订单组相关功能 rest接口")
@CacheConfig(cacheNames = "ccr:orderGroup")
public class OrderGroupController {
    @Resource
    OrderGroupService orderGroupService;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @ApiOperation(value = "获订单组列表",httpMethod = "GET",nickname = "listOrderGroupByPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                               @RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type = "integer",example = "1") int coinPairChoiceId){
        return this.orderGroupService.listByPage(pageNum, pageSizeCommon,coinPairChoiceId);
    }
    @ApiOperation(value = " 查询 订单组name 方法",httpMethod = "GET",nickname = "searchTradeOrderByCondition")
    @GetMapping("/search_group")
    public OpenSearchPage searchTradeRecordByCondition(@RequestParam(value = "startTime",defaultValue = "0") @ApiParam(value = "开始时间 ",required = true,type = "Long") Long startTime,
                                                       @RequestParam(value = "endTime",defaultValue = "0") @ApiParam(value = "截止时间",required = true,type = "Long") Long endTime,
                                                       @RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type="integer",example = "1") int coinPairChoiceId,
                                                       @RequestParam(value = "pageNum" , defaultValue = "1") int pageNum,
                                                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return this.orderGroupService.searchTradeRecordByCondition(startTime, endTime, coinPairChoiceId, pageNum, pageSize);
    }

    @ApiOperation(value = "获取单个订单组信息",httpMethod = "GET",nickname = "getOneOrderGroupById")
    @GetMapping("/{id}")
    public OrderGroup getById(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int orderGroupId){

        return this.orderGroupService.getOneById(orderGroupId);
    }

    @ApiOperation(value = "添加单个订单组信息",httpMethod = "POST",nickname = "addOneOrderGroup")
    @PostMapping("/")
    public Result addOneOrderGroup(@RequestBody @ApiParam(value = "交易订单组", required = true, type = "string") OrderGroup orderGroup){
        if (orderGroup.getCoinPairChoiceId() != 0){
            if (coinPairChoiceService.getByDisregardStatus(orderGroup.getCoinPairChoiceId()) == null){
                return new Result<>(null,"该自选币不存在！");
            }
        }else {
            return new Result<>(null,"该自选币id不合法");
        }
        if (this.orderGroupService.checkExistByGroupName(orderGroup.getName()).get() > 0){
            OrderGroup orderGroup1 = this.orderGroupService.getOneByName(orderGroup.getName());
            int orderGroupId = orderGroup1.getIsEnd() == 1?0:orderGroup1.getId();
            return new Result<>(0-orderGroupId,"订单组name已存在！");

        }
        if (this.orderGroupService.checkExistByCoinPairChoiceIdAndIsEnd(orderGroup.getCoinPairChoiceId()).get() > 0){
            return new Result<>(null,"该自选币有未结单的订单组，创建订单组失败！");
        }
        return new Result<>(this.orderGroupService.add(orderGroup));
    }

    @ApiOperation(value = "更新单个交易订单组信息",httpMethod = "PUT",nickname = "updateOrderGroupById")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string") OrderGroup orderGroup
                         ){
        if (this.orderGroupService.checkExistByID(orderGroup.getId()).get() <= 0){
            return new Result<>(null,"订单组不存在，更新订单组失败！");
        }
        if (this.orderGroupService.getOneById(orderGroup.getId()).getIsEnd() > 0){
            return new Result<>(null,"该订单组已结单，更新订单组失败！");
        }
        Optional<Integer> result = this.orderGroupService.update(orderGroup);
        if (result.get() == CommonConstantUtil.VERIFY_FAIL){
            return new Result<>(null,"不能操作不是自己的东西哦！");
        }
        return new Result<>(result);
    }

    @ApiOperation(value = "删除单个订单组信息",httpMethod = "DELETE",nickname = "deleteOneOrderGroupById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int orderGroupId){
        if (this.orderGroupService.checkExistByID(orderGroupId).get() <= 0){
            return new Result<>(null,"订单组不存在！");
        }
        Optional<Integer> result = this.orderGroupService.delete(orderGroupId);
        if (result.get() == CommonConstantUtil.VERIFY_FAIL){
            return new Result<>(null,"不能删除不是自己的东西哦！😯");
        }
        return new Result<>(result);
    }

    @ApiOperation(value = "根据 ID 把 orderGroup 添加/更新 到openSearch",httpMethod = "PUT",nickname = "pushOrderGroupToOpenSearchById")
    @PutMapping("/to_open_search_by_id")
    public Result<Integer> pushToOpenSearchById(@RequestParam("id") @ApiParam(value = "订单组id",required = true,type = "integer",example = "1") @Min(1) int id) {
        return new Result(this.orderGroupService.updateOpenSearchFromSql(id));
    }

    @ApiOperation(value = "根据 自选币ID 获取交易总览",httpMethod = "GET",nickname = "getCoinPairChoiceTradeOverview")
    @GetMapping("/trade_overview")
    public OrderGroupOverviewResult getCoinPairChoiceTradeOverview(@RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type = "integer",example = "1") int coinPairChoiceId){
        return this.orderGroupService.tradeOverview(coinPairChoiceId);
    }

    @ApiIgnore
    @GetMapping("/name")
    public int getIdByName(@RequestParam("name") String name){
        return this.orderGroupService.getGroupIdByName(name);
    }

    @GetMapping("/record_group")
    public Result addOrUpdateOneOrderGroup(@RequestParam("id") int id,
                                           @RequestParam("name") String name,
                                           @RequestParam("coinPairChoiceId") int coinPairChoiceId,
                                           @RequestParam("endProfitRatio")  double endProfitRatio,
                                           @RequestParam("isEnd")  int isEnd,
                                           @RequestParam("endType") int endType,
                                           @RequestParam("sign") int sign){
        OrderGroup orderGroup = loadingOrderGroup(name, coinPairChoiceId, endProfitRatio, isEnd, endType);
        if (sign == CommonConstantUtil.ADD_SIGN){
            return addOneOrderGroup(orderGroup);
        }
        if (sign == CommonConstantUtil.UPDATE_SIGN && id > 0){
            orderGroup.setId(id);
            return update(orderGroup);
        }
        return new Result<>(null,"sign不合法！");
    }
    private OrderGroup loadingOrderGroup(String name,int coinPairChoiceId,double endProfitRatio,int isEnd,int endType){
        OrderGroup orderGroup = new OrderGroup();

        orderGroup.setName(name);
        orderGroup.setCoinPairChoiceId(coinPairChoiceId);
        orderGroup.setEndProfitRatio(endProfitRatio);
        orderGroup.setIsEnd(isEnd);
        orderGroup.setEndType(endType);

        return orderGroup;
    }
}
