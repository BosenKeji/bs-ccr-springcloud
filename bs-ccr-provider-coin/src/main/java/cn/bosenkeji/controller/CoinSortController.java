package cn.bosenkeji.controller;

import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.service.ITradePlatformClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;


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
    ITradePlatformClientService iTradePlatformClientService;

    @Resource
    DiscoveryClient discoveryClient;

    @ApiOperation(value = "根据交易平台id获取货币排序列表接口",httpMethod = "GET",nickname = "getCoinSortByTradePlatformId")
    @GetMapping("/{tradePlatformId}")
    public PageInfo listCoinSortByTradePlatformId(@PathVariable( value="tradePlatformId") @ApiParam(value = "交易平台id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                                                  @RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                                  @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinSortService.listByTradePlatformId(tradePlatformId, pageNum, pageSizeCommon);
    }


    @ApiOperation(value = "添加单个货币排序接口",httpMethod = "POST",nickname = "addOneCoinSort")
    @PostMapping("/")
    public Result add(@RequestParam("tradePlatformName") @ApiParam(value = "交易平台名字", required = true, type = "string") String tradePlatformName,
                      @RequestParam( value="coinId") @ApiParam(value = "货币id", required = true, type = "integer" ,example = "1") int coinId,
                      @RequestParam( value="type") @ApiParam(value = "货币类型，1:计价 2: 交易", required = true, type = "integer" ,example = "1") int type){
        TradePlatform tradePlatform = this.iTradePlatformClientService.getTradePlatformByName(tradePlatformName);
        if (tradePlatform.getId() == 0){
            return new Result<>(null,"平台不存在！");
        }
        int tradePlatformId = tradePlatform.getId();
        if (this.coinSortService.checkByTradePlatformIdAndCoinId(tradePlatformId,coinId).get() >= 1){
            return new Result<>(null,"货币排序实体已存在");
        }

        CoinSort coinSort = new CoinSort();
        coinSort.setTradePlatformId(tradePlatformId);
        coinSort.setCoinId(coinId);
        coinSort.setSortNum(coinId);
        coinSort.setType(type);
        coinSort.setStatus(1);
        coinSort.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinSortService.add(coinSort));
    }

    @ApiOperation(value = "更新货币排序接口",httpMethod = "PUT",nickname = "updateCoinSort")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "货币排序实体", required = true, type = "String" ) CoinSort coinSort){

        if (this.coinSortService.get(coinSort.getId()) == null){
            return new Result<>(null,"货币排序实体不存在");
        }

        coinSort.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinSortService.update(coinSort));
    }

    @ApiOperation(value = "删除货币排序接口",httpMethod = "DELETE",nickname = "deleteOneCoinSort")
    @DeleteMapping("/")
    public Result delete(@RequestParam("tradePlatformId") @ApiParam(value = "交易平台id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                         @RequestParam("coinId") @ApiParam(value = "货币id", required = true, type = "integer" ,example = "1") int coinId){

        if (this.coinSortService.checkByTradePlatformIdAndCoinId(tradePlatformId,coinId).get() < 1){
            return new Result<>(null,"货币排序实体不存在");
        }

        return new Result<>(this.coinSortService.delete(tradePlatformId, coinId));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }
}
