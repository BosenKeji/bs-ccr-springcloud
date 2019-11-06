package cn.bosenkeji.controller;

import cn.bosenkeji.service.OrderGroupService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.OrderGroup;
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

    @ApiOperation(value = "获订单组列表",httpMethod = "GET",nickname = "listOrderGroupByPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                               @RequestParam("coinPairChoiceId")@ApiParam(value = "自选币id",required = true,type = "integer",example = "1") int coinPairChoiceId){
        return this.orderGroupService.listByPage(pageNum, pageSizeCommon,coinPairChoiceId);
    }

    @ApiOperation(value = "获取单个订单组信息",httpMethod = "GET",nickname = "getOneOrderGroupById")
    @GetMapping("/{id}")
    public OrderGroup getById(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int orderGroupId){

        return this.orderGroupService.getOneById(orderGroupId);
    }

    @ApiOperation(value = "添加单个订单组信息",httpMethod = "POST",nickname = "addOneOrderGroup")
    @PostMapping("/")
    public Result addOneOrderGroup(@RequestBody @ApiParam(value = "交易订单组", required = true, type = "string") OrderGroup orderGroup){
        if (this.orderGroupService.checkExistByCoinPairChoiceIdAndIsEnd(orderGroup.getCoinPairChoiceId()).get() > 0){
            return new Result<>(null,"该自选币有未结单的订单组，创建订单组失败！");
        }
        return new Result<>(this.orderGroupService.add(orderGroup));
    }

    @ApiOperation(value = "更新单个交易订单组信息",httpMethod = "PUT",nickname = "updateOrderGroupById")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "交易订单实体", required = true, type = "string") OrderGroup orderGroup){
        if (this.orderGroupService.checkExistByID(orderGroup.getId()).get() <= 0){
            return new Result<>(null,"订单组不存在，更新订单组失败！");
        }
        return new Result<>(this.orderGroupService.update(orderGroup));
    }

    @ApiOperation(value = "删除单个订单组信息",httpMethod = "DELETE",nickname = "deleteOneOrderGroupById")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "交易订单组id", required = true, type = "integer",example = "1") int orderGroupId){
        if (this.orderGroupService.checkExistByID(orderGroupId).get() > 0){
            return new Result<>(null,"订单组不存在！");
        }
        return new Result<>(this.orderGroupService.delete(orderGroupId));
    }
}
