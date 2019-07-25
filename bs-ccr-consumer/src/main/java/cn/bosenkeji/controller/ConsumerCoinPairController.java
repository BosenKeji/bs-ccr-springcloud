package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 15:43
 */
@RestController
@RequestMapping("/coin_pair")
@Api(tags = "CoinPair 货币对相关接口", value = "提供货币对相关接口的 Rest API")
public class ConsumerCoinPairController {

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @ApiOperation(value = "获取单个货币对接口",httpMethod = "GET",nickname = "getCoinPairByPage")
    @GetMapping("/{id}")
    public CoinPair getCoinPair(@PathVariable("id") @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id) {
        return iCoinPairClientService.getCoinPair(id);
    }

    @ApiOperation(value = "获取货币对列表分页接口",httpMethod = "GET",nickname = "getOneCoinPair")
    @GetMapping("/")
    public PageInfo listCoinPair(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinPairClientService.listCoinPair(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "添加单个货币对接口",httpMethod = "POST",nickname = "addOneCoinPair")
    @PostMapping("/")
    public Result addCoinPair(@RequestBody @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair) {

        return iCoinPairClientService.addCoinPair(coinPair);
    }

    @ApiOperation(value = "更新单个货币对接口",httpMethod = "PUT",nickname = "updateOneCoinPair")
    @PutMapping("/")
    public Result updateCoinPair(@RequestBody @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){
        return iCoinPairClientService.updateCoinPair(coinPair);
    }

    @ApiOperation(value = "删除单个货币对接口",httpMethod = "DELETE",nickname = "deleteOneCoinPair")
    @DeleteMapping("/{id}")
    public Result deleteCoinPair(@PathVariable @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){
        return iCoinPairClientService.deleteCoinPair(id);
    }
}
