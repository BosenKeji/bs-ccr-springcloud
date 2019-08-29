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
import java.util.List;

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

    @ApiOperation(value = "根据货币ID获取货币对货币列表接口",httpMethod = "GET",nickname = "getCoinPairCoinListByCoinId")
    @GetMapping("/list_by_coinId")
    public List<CoinPairCoin> listByCoinId(@RequestParam( value="coinId",defaultValue="1")  @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId){
        return this.coinPairCoinService.listByCoinId(coinId);
    }

    @ApiOperation(value = "获取单个货币对货币列表接口",nickname = "getOneCoinPairCoin",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinPairCoin get(@PathVariable("id") @Min(1) @ApiParam(value = "货币对货币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairCoinService.get(id);
    }

    @ApiOperation(value = "添加货币对货币接口",httpMethod = "POST",nickname = "addOneCoinPairCoin")
    @PostMapping("/")
    public Result add(@RequestBody @Valid @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){

        if (this.coinPairCoinService.checkByCoinIdAndCoinPairId(coinPairCoin.getCoinId(),coinPairCoin.getCoinPairId()).get() >= 1){
            return new Result<>(null,"货币对货币已存在");
        }

        coinPairCoin.setStatus(1);
        coinPairCoin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return new Result<>(this.coinPairCoinService.add(coinPairCoin));
    }

    @ApiOperation(value = "更新货币对货币接口",httpMethod = "PUT",nickname = "updateCoinPairCoin")
    @PutMapping("/")
    public Result update(@RequestBody @Valid @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){

        if (this.coinPairCoinService.get(coinPairCoin.getId()) == null){
            return new Result<>(null,"货币对货币id不存在");
        }
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairCoinService.update(coinPairCoin));
    }

    @ApiOperation(value = "删除货币对货币接口",httpMethod = "DELETE",nickname = "deleteCoinPairCoin")
    @DeleteMapping("/")
    public Result delete(@RequestParam("coinId") @Min(1) @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId,
                                    @RequestParam("coinPairId") @Min(1) @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int coinPairId){

        if (this.coinPairCoinService.checkByCoinIdAndCoinPairId(coinId,coinPairId).get() < 1){
            return new Result<>(null,"货币对货币不存在");
        }

        return new Result<>(this.coinPairCoinService.delete(coinId, coinPairId));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }

}
