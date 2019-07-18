package cn.bosenkeji.controller;


import cn.bosenkeji.service.CoinPairDealService;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/coinpairdeal")
@Validated
public class CoinPairDealController {

    @Autowired
    private CoinPairDealService coinPairDealService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @Resource
    private DiscoveryClient client;


    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取货币对交易信息",notes = "指定用户ID，获取其所有的交易信息")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(@PathVariable("userId") @Min(1) Integer userId) {
        return coinPairDealService.findCoinPairDealByUserId(userId, 0 ,pageSizeCommon);
    }

    @RequestMapping(value = "/{userId}/choic/{choicId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定货币对交易信息",notes = "指定用户ID和货币对ID，获取该用户某货币对交易信息")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") @Min(1) Integer userId,
            @PathVariable("choicId") @Min(1) Integer choicId
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndChoicId(userId,choicId,0,pageSizeCommon);
    }


    @RequestMapping(value = "/{userId}/type/{type}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定类型的交易信息",notes = "指定用户ID和交易类型，获取对应的交易信息")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") @Min(1) Integer userId,
            @PathVariable("type") @Min(1) @Max(2) Integer type
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndType(userId,type,0,pageSizeCommon);
    }

    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    @ApiOperation(value = "更新货币对交易状态",notes = "指定货币对交易ID，通过ID更新交易状态")
    public boolean updateCoinPairDealStartsById(@Param("id") @Min(1) Integer id ,@Param("status") @Min(1) Integer status) {
        return coinPairDealService.updateCoinPairDealStartsById(id,status);
    }

    @RequestMapping(value = "/count/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取交易货币对数量" , notes = "指定用户ID，获取其交易货币对的总数")
    public int countCoinPair(@PathVariable("userId") @Min(1) Integer userId) {
        return coinPairDealService.countCoinPair(userId);
    }

    @RequestMapping(value = "/count/{userId}/choic/{choicId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取货币对交易的下单数",notes = "指定用户ID和货币对ID，统计已下单数")
    public int countCoinPairDeal(@PathVariable("userId") @Min(1) Integer userId, @PathVariable("choicId") @Min(1) Integer choicId) {
        return coinPairDealService.countCoinPairDeal(userId,choicId);
    }


    @RequestMapping(value = "/discover" , method = RequestMethod.GET)
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
