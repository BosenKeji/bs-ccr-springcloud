package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@Validated
@RequestMapping("/coin_sort")
@Api(tags = "CoinSort 货币排序接口" ,value = "提供货币排序的 Rest API")
@RefreshScope
public class ConsumerCoinSortController {

    @Autowired
    ICoinSortClientService iCoinSortClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "根据交易平台id获取货币排序列表接口",httpMethod = "GET",nickname = "getOneCoinSort")
    @GetMapping("/")
    public PageInfo listProduct(@RequestParam( value="tradePlatformId")@Min(1) @ApiParam(value = "交易平台id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                                @RequestParam("type") @ApiParam(value = "货币类型 1是计价 2是基础", required = true, type = "integer" ,example = "1")int type,
                                @RequestParam( value="pageNum",defaultValue="1")@Min(1) int pageNum) {
        pageSizeCommon = 20;
        return iCoinSortClientService.listCoinSortByTradePlatformId(tradePlatformId,type,pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "添加单个货币排序接口",httpMethod = "POST",nickname = "addOneCoinSort")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result addCoin(@RequestParam("tradePlatformName")@NotNull @ApiParam(value = "交易平台名字", required = true, type = "string") String tradePlatformName,
                          @RequestParam( value="coinId")@Min(1) @ApiParam(value = "货币id", required = true, type = "integer" ,example = "1") int coinId,
                          @RequestParam( value="type") @Min(1) @Max(2) @ApiParam(value = "货币类型，1:计价 2: 交易", required = true, type = "integer" ,example = "1") int type) {

        return iCoinSortClientService.addCoinSort(tradePlatformName, coinId, type);
    }

    @ApiOperation(value = "更新货币排序接口",httpMethod = "PUT",nickname = "updateCoinSort")
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result updateCoin(@RequestBody @ApiParam(value = "货币排序id", required = true, type = "String" )@NotNull CoinSort coinSort){
        return iCoinSortClientService.updateCoinSort(coinSort);
    }

    @ApiOperation(value = "删除货币排序接口",httpMethod = "DELETE",nickname = "deleteOneCoinSort")
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result deleteCoinSort(@RequestParam("tradePlatformId") @Min(1) @ApiParam(value = "交易平台id", required = true, type = "integer" ,example = "1") int tradePlatformId,
                                 @RequestParam("coinId")@Min(1) @ApiParam(value = "货币id", required = true, type = "integer" ,example = "1") int coinId){
        return iCoinSortClientService.deleteCoinSort(tradePlatformId, coinId);
    }
}
