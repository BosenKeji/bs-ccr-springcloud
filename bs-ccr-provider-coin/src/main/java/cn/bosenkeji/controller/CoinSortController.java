package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinSortEnum;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 14:23
 */
@RestController
@RequestMapping("/coin_sort")
@Api(tags = "CoinSort 货币排序接口" ,value = "提供货币排序的 Rest API")
public class CoinSortController {

    @Resource
    CoinSortService coinSortService;

    @Resource
    DiscoveryClient discoveryClient;


    @ApiOperation(value = "根据交易平台获取货币排序列表接口",httpMethod = "GET",nickname = "getCoinSortByTradePlatformId")
    @GetMapping("/{tradePlatformId}")
    public PageInfo listCoinSortByTradePlatformId(@PathVariable( value="tradePlatformId") @ApiParam(value = "货币排序id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                                                  @RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                  @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinSortService.listByTradePlatformId(tradePlatformId, pageNum, pageSizeCommon);
    }


    @ApiOperation(value = "添加单个货币排序接口",httpMethod = "POST",nickname = "addOneCoinSort")
    @PostMapping("/")
    public Result add(@RequestBody @ApiParam(value = "货币排序实体", required = true, type = "String" ) CoinSort coinSort){
        this.coinSortService.checkByTradePlatformIdAndCoinId(coinSort.getTradePlatformId(),coinSort.getCoinId())
                .filter((value)->value==0)
                .orElseThrow(()->new AddException(CoinSortEnum.NAME));

        coinSort.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinSortService.add(coinSort)
                .filter((value)->value>=1)
                .orElseThrow(()->new AddException(CoinSortEnum.NAME)));
    }

    @ApiOperation(value = "更新货币排序接口",httpMethod = "PUT",nickname = "updateCoinSort")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "货币排序实体", required = true, type = "String" ) CoinSort coinSort){

        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinSortService.update(coinSort)
                .filter((value)->value>=1)
                .orElseThrow(()->new UpdateException(CoinSortEnum.NAME)));
    }

    @ApiOperation(value = "删除货币排序接口",httpMethod = "DELETE",nickname = "deleteOneCoinSort")
    @DeleteMapping("/")
    public Result delete(@RequestParam("tradePlatformId") @ApiParam(value = "交易平台id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                         @RequestParam("coinId") @ApiParam(value = "货币id", required = true, type = "integer" ,example = "1") int coinId){

        return new Result<>(this.coinSortService.delete(tradePlatformId, coinId)
                .filter((value)->value>=1)
                .orElseThrow(()->new AddException(CoinSortEnum.NAME)));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }
}
