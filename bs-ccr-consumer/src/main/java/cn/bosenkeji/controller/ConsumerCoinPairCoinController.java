package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 15:47
 */
@RestController
@RequestMapping("/coin_pair_coin")
@Api(tags = "CoinPairCoin 货币对货币相关接口", value = "提供货币对货币相关接口的 Rest API")
@RefreshScope
public class ConsumerCoinPairCoinController {

    @Resource
    ICoinPairCoinClientService iCoinPairCoinClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取货币对货币列表接口",httpMethod = "GET",nickname = "getCoinPairCoinListWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum){
        return this.iCoinPairCoinClientService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "添加货币对货币接口",httpMethod = "POST",nickname = "addOneCoinPairCoin")
    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result addCoinPairCoin(@RequestBody @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin) {

        return iCoinPairCoinClientService.addCoinPairCoin(coinPairCoin);
    }

    @ApiOperation(value = "更新货币对货币接口",httpMethod = "PUT",nickname = "updateCoinPairCoin")
    @PutMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result updateCoinPairCoin(@RequestBody @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){
        return iCoinPairCoinClientService.updateCoinPairCoin(coinPairCoin);
    }

    @ApiOperation(value = "删除货币对货币接口",httpMethod = "DELETE",nickname = "deleteCoinPairCoin")
    @DeleteMapping("/")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Result deleteCoinPairCoin(@RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId,
                                                @RequestParam("coinPairId") @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int coinPairId){
        return iCoinPairCoinClientService.deleteCoinPairCoin(coinId, coinPairId);
    }
}
