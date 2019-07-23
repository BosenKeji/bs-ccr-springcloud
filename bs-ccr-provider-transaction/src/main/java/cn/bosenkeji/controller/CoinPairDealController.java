package cn.bosenkeji.controller;


import cn.bosenkeji.service.CoinPairDealService;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping("/coin_pair_deal")
@Validated
@Api(tags = "coin_pair_deal 货币对交易相关接口", value = "提供货币对交易的相关接口 Rest API")
public class CoinPairDealController {

    @Autowired
    private CoinPairDealService coinPairDealService;


    @Resource
    private DiscoveryClient client;


    @GetMapping(value = "/{userId}")
    @ApiOperation(value = "获取货币对交易信息",notes = "指定用户ID，获取其所有的交易货币对的信息",
            nickname = "findCoinPairDealByUserId",httpMethod = "GET")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(
            @PathVariable("userId") @Min(1) Integer userId,
            @RequestParam("pageNum") @Min(1) Integer pageNum,
            @RequestParam("pageSize") @Min(1) Integer pageSize
            ) {
        return coinPairDealService.findCoinPairDealByUserId(userId, pageNum ,pageSize);
    }

    @PostMapping(value = "/{userId}/choice/{choiceId}")
    @ApiOperation(value = "获取指定货币对交易信息",notes = "指定用户ID和货币对ID，获取该用户某货币对交易信息",
            nickname = "findCoinPairDealByUserIdAndChoiceId",httpMethod = "GET")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(
            @PathVariable("userId") @Min(1) Integer userId,
            @PathVariable("choiceId") @Min(1) Integer choicId,
            @RequestParam("pageNum") @Min(1) Integer pageNum,
            @RequestParam("pageSize") @Min(1) Integer pageSize
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndChoiceId(userId,choicId,pageNum,pageSize);
    }


    @GetMapping(value = "/{userId}/type/{type}")
    @ApiOperation(value = "获取指定类型的交易信息",notes = "指定用户ID和交易类型，获取对应的交易信息",
            nickname = "findCoinPairDealByUserIdAndType",httpMethod = "GET")
    public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(
            @PathVariable("userId") @Min(1) Integer userId,
            @PathVariable("type") @Min(1) @Max(2) Integer type,
            @RequestParam("pageNum") @Min(1) Integer pageNum,
            @RequestParam("pageSize") @Min(1) Integer pageSize
    ) {
        return coinPairDealService.findCoinPairDealByUserIdAndType(userId,type,pageNum,pageSize);
    }

    @PutMapping(value = "/status")
    @ApiOperation(value = "更新货币对交易状态",notes = "指定货币对交易ID，通过ID更新交易状态",
            nickname = "updateCoinPairDealStartsById",httpMethod = "PUT")
    public Optional<Integer> updateCoinPairDealStartsById(@RequestParam("id") @Min(1) Integer id , @RequestParam("status") @Min(1) Integer status) {
        return coinPairDealService.updateCoinPairDealStartsById(id,status);
    }

    @GetMapping(value = "/count/{userId}")
    @ApiOperation(value = "获取交易货币对数量" , notes = "指定用户ID，获取其交易货币对的总数",
            nickname = "countCoinPair",httpMethod = "GET")
    public Optional<Integer> countCoinPair(@PathVariable("userId") @Min(1) Integer userId) {
        return coinPairDealService.countCoinPair(userId);
    }

    @GetMapping(value = "/count/{userId}/choice/{choiceId}")
    @ApiOperation(value = "获取货币对交易的下单数",notes = "指定用户ID和货币对ID，统计已下单数",
            nickname = "countCoinPairDeal",httpMethod = "GET")
    public Optional<Integer> countCoinPairDeal(@PathVariable("userId") @Min(1) Integer userId, @PathVariable("choiceId") @Min(1) Integer choicId) {
        return coinPairDealService.countCoinPairDeal(userId,choicId);
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "添加货币对交易信息",notes = "对于一个货币对每下一单的信息",
            nickname = "insertCoinPairDealBySelective",httpMethod = "POST")
    public Optional<Integer> insertCoinPairDealBySelective(@RequestBody @NotNull CoinPairDeal coinPairDeal) {
        return coinPairDealService.insertCoinPairDealBySelective(coinPairDeal);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除指定id的货币对交易信息",notes = "删除指定id的交易信息",
            nickname = "deleteCoinPairDealByPrimaryKey",httpMethod = "DELETE"
    )
    public Optional<Integer> deleteCoinPairDealByPrimaryKey(@PathVariable("id") @Min(1) Integer id) {
        return coinPairDealService.deleteCoinPairDealByPrimaryKey(id);
    }

    @DeleteMapping(value = "/{userId}/choice/{choiceId}")
    @ApiOperation(value = "批量删除货币对交易信息",notes = "指定用户的Id和货币对的Id，删除匹配的货币对交易信息",
            nickname = "deleteBatchCoinPairDealByUserIdAndCoinPairId",httpMethod = "DELETE"
    )
    public Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(
            @PathVariable("userId") @Min(1) Integer userId, @PathVariable("choiceId") @Min(1) Integer choicId
    ) {
        return coinPairDealService.deleteBatchCoinPairDealByUserIdAndChoiceId(userId,choicId);
    }

    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口",
            nickname = "discover",httpMethod = "GET")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
