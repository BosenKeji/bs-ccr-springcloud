package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 15:43
 */
@RestController
@RequestMapping("/coin_pair")
@Api(tags = "CoinPair 货币对相关接口", value = "提供货币对相关接口的 Rest API")
@RefreshScope
public class ConsumerCoinPairController {

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "根据货币对name获取货币对信息接口",httpMethod = "GET",nickname = "getOneCoinPairByName")
    @GetMapping("/by_name/{name}")
    public CoinPair getOneCoinPairByName(@PathVariable("name")   @ApiParam(value = "货币对name", required = true, type = "string") String name){
        return this.iCoinPairClientService.getCoinPairByName(name);
    }

    @ApiOperation(value = "获取单个货币对接口",httpMethod = "GET",nickname = "getCoinPairByPage")
    @GetMapping("/{id}")
    public CoinPair getCoinPair(@PathVariable("id") @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id) {
        return iCoinPairClientService.getCoinPair(id);
    }

    @ApiOperation(value = "获取货币对列表分页接口",httpMethod = "GET",nickname = "getOneCoinPair")
    @GetMapping("/")
    public PageInfo listCoinPair(@RequestParam( value="pageNum",defaultValue="1") int pageNum) {
        return iCoinPairClientService.listCoinPair(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "添加单个货币对接口",httpMethod = "POST",nickname = "addOneCoinPair")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result addCoinPair(@RequestBody @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair) {

        return iCoinPairClientService.addCoinPair(coinPair);
    }

    @ApiOperation(value = "更新单个货币对接口",httpMethod = "PUT",nickname = "updateOneCoinPair")
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result updateCoinPair(@RequestBody @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){
        return iCoinPairClientService.updateCoinPair(coinPair);
    }

    @ApiOperation(value = "删除单个货币对接口",httpMethod = "DELETE",nickname = "deleteOneCoinPair")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result deleteCoinPair(@PathVariable @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){
        return iCoinPairClientService.deleteCoinPair(id);
    }
}
