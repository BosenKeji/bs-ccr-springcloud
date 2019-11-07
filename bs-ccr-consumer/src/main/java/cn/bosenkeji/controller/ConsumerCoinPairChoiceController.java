package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.TokenUser;
import cn.bosenkeji.service.ICoinPairChoiceClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 17:02
 */
@RestController
@Validated
@RequestMapping("/coin_pair_choice")
@Api(tags = "CoinPairChoice 自选货币接口",value = "自选货币相关功能 Rest接口")
@RefreshScope
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
public class ConsumerCoinPairChoiceController {
    @Resource
    ICoinPairChoiceClientService iCoinPairChoiceClientService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoiceWithPage")
    @GetMapping("/")
    public PageInfo getListCoinPairChoiceWithPage(@RequestParam(value="pageNum",defaultValue="1") @Min(1) int pageNum,
                                                  @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                                                 @RequestParam("coinId") @Min(1) @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId){
        pageSizeCommon = 100;
        return this.iCoinPairChoiceClientService.getListCoinPairChoiceWithPage(pageNum, pageSizeCommon,tradePlatformApiBindProductComboId,coinId);
    }

    @ApiOperation(value = "检查自选币",httpMethod = "GET",nickname = "checkExistByCoinPairNameAndTradePlatformApiBindProductComboId")
    @GetMapping("/check_coin_pair_choice")
    public Result checkExistByCoinPairNameAndTradePlatformApiBindProductComboId(@RequestParam("coinPairName")   @ApiParam(value = "货币对Name", required = true, type = "String") @NotNull String coinPairName,
                                                  @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
       return this.iCoinPairChoiceClientService.checkExistByCoinPairNameAndTradePlatformApiBindProductComboId(coinPairName, tradePlatformApiBindProductComboId);

    }

    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoice")
    @GetMapping("/{id}")
    public CoinPairChoice getOneCoinPairChoice(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.iCoinPairChoiceClientService.getOneCoinPairChoice(id);
    }

    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoice")
    @PostMapping("/")
    public Result addOneCoinPairChoice(@RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                                       @RequestParam("isStrategy")  @ApiParam(value = "是否开始策略", required = true, type = "integer",example = "1") @Min(0) int isStrategy,
                                       @RequestParam("coinPairId") @Min(1) @ApiParam(value = "货币对id", required = true, type = "integer",example = "1") int coinPairId){
        return this.iCoinPairChoiceClientService.addOneCoinPairChoice(tradePlatformApiBindProductComboId, isStrategy, coinPairId);
    }

    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoice")
    @PutMapping("/")
    public Result updateCoinPairChoice(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") @NotNull CoinPairChoice coinPairChoice){
        return this.iCoinPairChoiceClientService.updateCoinPairChoice(coinPairChoice);
    }

    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoice")
    @DeleteMapping("/{id}")
    public Result deleteOneCoinPairChoice(@PathVariable("id") @Min(1) @ApiParam(value = "自选币 ID", required = true, type = "integer",example = "1") int id,
                                          @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        return this.iCoinPairChoiceClientService.deleteOneCoinPairChoice(id,tradePlatformApiBindProductComboId);
    }

    @ApiOperation(value = "批量删除自选货币接口",httpMethod = "DELETE",nickname = "batchDeleteOneCoinPairChoice")
    @DeleteMapping("/batch")
    public Result batchDelete(@RequestParam("coinPairChoiceIds") @ApiParam(value = "自选币ID字符串 ", required = true, type = "string") @NotNull String coinPairChoiceIds,
                              @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        return this.iCoinPairChoiceClientService.batchDelete(coinPairChoiceIds,tradePlatformApiBindProductComboId);
    }

    @ApiOperation(value = "根据计价货币id查询有买卖记录的货币对",httpMethod = "GET",nickname = "recordByCoinId")
    @GetMapping("/record")
    public PageInfo recordByCoinId (@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                                    @RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") @Min(1) int coinId,
                                    @RequestParam("type") @ApiParam(value = "收益记录（'profit'）还是买入日志（'buy'）", required = true, type = "String") @NotNull String type){
        return this.iCoinPairChoiceClientService.recordByCoinId(pageNum, pageSizeCommon, tradePlatformApiBindProductComboId, coinId, type);
    }
}
