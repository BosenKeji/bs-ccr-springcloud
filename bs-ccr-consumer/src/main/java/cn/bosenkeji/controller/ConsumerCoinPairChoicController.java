package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoicClientService;
import cn.bosenkeji.vo.CoinPair;
import cn.bosenkeji.vo.CoinPairChoic;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author CAJR
 * @create 2019/7/22 17:02
 */
@RestController
@RequestMapping("/consumer/coinpairchoic")
@Api(tags = "CoinPairChoic 自选货币接口",value = "自选货币相关功能 Rest接口")
public class ConsumerCoinPairChoicController {
    @Resource
    ICoinPairChoicClientService iCoinPairChoicClientService;

    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoicWithPage")
    @GetMapping("/")
    public PageInfo getListCoinPairChoicWithPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.iCoinPairChoicClientService.getListCoinPairChoicWithPage(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoic")
    @GetMapping("/{id}")
    public CoinPairChoic getOneCoinPairChoic(@PathVariable("id")  @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.iCoinPairChoicClientService.getOneCoinPairChoic(id);
    }

    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoic")
    @PostMapping("/")
    public boolean addOneCoinPairChoic(@RequestParam("userId")  @ApiParam(value = "用户id", required = true, type = "string") int userId,
                                       @RequestParam("strategyId")  @ApiParam(value = "策略状态", required = true, type = "string") int strategyStatus,
                                       @RequestParam("coinPairId")  @ApiParam(value = "货币对id", required = true, type = "string") int coinPairId){
        return this.iCoinPairChoicClientService.addOneCoinPairChoic(userId, strategyStatus, coinPairId);
    }

    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoic")
    @PutMapping("/")
    public boolean updateCoinPairChoic(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") CoinPairChoic coinPairChoic){
        return this.iCoinPairChoicClientService.updateCoinPairChoic(coinPairChoic);
    }

    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoic")
    @DeleteMapping("/{id}")
    public boolean deleteOneCoinPairChoic(@PathVariable("id") @ApiParam(value = "自选币 ID", required = true, type = "integer",example = "1") int id){
        return this.iCoinPairChoicClientService.deleteOneCoinPairChoic(id);
    }
}
