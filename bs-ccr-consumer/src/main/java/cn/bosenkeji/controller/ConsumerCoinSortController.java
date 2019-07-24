package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/consumer/coin_sort")
@Api(tags = "CoinSort 货币排序接口" ,value = "提供货币排序的 Rest API")
public class ConsumerCoinSortController {

    @Autowired
    ICoinSortClientService iCoinSortClientService;

    @ApiOperation(value = "获取货币排序列表接口",httpMethod = "GET",nickname = "getCoinSortByPage")
    @GetMapping("/{id}")
    public CoinSort getCoinSort(@PathVariable("id") @ApiParam(value = "货币排序id", required = true, type = "integer" ,example = "1") int id) {
        return iCoinSortClientService.getCoinSort(id);
    }

    @ApiOperation(value = "获取单个货币排序接口",httpMethod = "GET",nickname = "getOneCoinSort")
    @GetMapping("/")
    public PageInfo listProduct(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinSortClientService.listCoinSort(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "添加单个货币排序接口",httpMethod = "POST",nickname = "addOneCoinSort")
    @PostMapping("/")
    public Result addCoin(@RequestBody @ApiParam(value = "货币排序id", required = true, type = "String" ) CoinSort coinSort) {

        return iCoinSortClientService.addCoinSort(coinSort);
    }

    @ApiOperation(value = "更新货币排序接口",httpMethod = "PUT",nickname = "updateCoinSort")
    @PutMapping("/")
    public Result updateCoin(@RequestBody @ApiParam(value = "货币排序id", required = true, type = "String" ) CoinSort coinSort){
        return iCoinSortClientService.updateCoinSort(coinSort);
    }

    @ApiOperation(value = "删除货币排序接口",httpMethod = "DELETE",nickname = "deleteOneCoinSort")
    @DeleteMapping("/{id}")
    public Result deleteCoinSort(@PathVariable("id") @ApiParam(value = "货币排序id", required = true, type = "integer" ,example = "1") int id){
        return iCoinSortClientService.deleteCoinSort(id);
    }
}
