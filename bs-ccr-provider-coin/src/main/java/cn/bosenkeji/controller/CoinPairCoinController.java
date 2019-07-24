package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairCoinEnum;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 12:14
 */
@RestController
@RequestMapping("/coin_pair_coin")
@Api(tags = "CoinPairCoin 货币对货币相关接口", value = "提供货币对货币相关接口的 Rest API")
public class CoinPairCoinController {

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    CoinPairCoinService coinPairCoinService;


    @ApiOperation(value = "获取货币对货币列表接口",httpMethod = "GET",nickname = "getCoinPairCoinListWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairCoinService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币对货币列表接口",nickname = "getOneCoinPairCoin",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinPairCoin get(@PathVariable("id") @Min(1) @ApiParam(value = "货币对货币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairCoinService.get(id).orElseThrow(()-> new NotFoundException(CoinPairCoinEnum.NAME));
    }

    @ApiOperation(value = "添加货币对货币接口",httpMethod = "POST",nickname = "addOneCoinPairCoin")
    @PostMapping("/")
    public Result add(@RequestBody @Valid @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){

        coinPairCoin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairCoinService.add(coinPairCoin)
                .filter((value)->value>=1)
                .orElseThrow(()->new AddException(CoinPairCoinEnum.NAME)));
    }

    @ApiOperation(value = "更新货币对货币接口",httpMethod = "PUT",nickname = "updateCoinPairCoin")
    @PutMapping("/")
    public Result update(@RequestBody @Valid @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairCoinService.update(coinPairCoin)
                .filter((value)->value>=1)
                .orElseThrow(()->new UpdateException(CoinPairCoinEnum.NAME)));
    }

    @ApiOperation(value = "删除货币对货币接口",httpMethod = "DELETE",nickname = "deleteCoinPairCoin")
    @DeleteMapping("/")
    public Result delete(@RequestParam("coinId") @Min(1) @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId,
                                    @RequestParam("coinPairId") @Min(1) @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int coinPairId){
        return new Result<>(this.coinPairCoinService.delete(coinId, coinPairId)
                .filter((value)->value>=1)
                .orElseThrow(()->new DeleteException(CoinPairCoinEnum.NAME)));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }

}
