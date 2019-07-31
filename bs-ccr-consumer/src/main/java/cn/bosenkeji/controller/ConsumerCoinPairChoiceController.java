package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoiceClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 17:02
 */
@RestController
@RequestMapping("/coin_pair_choice")
@Api(tags = "CoinPairChoice 自选货币接口",value = "自选货币相关功能 Rest接口")
public class ConsumerCoinPairChoiceController {
    @Resource
    ICoinPairChoiceClientService iCoinPairChoiceClientService;

    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoiceWithPage")
    @GetMapping("/")
    public PageInfo getListCoinPairChoiceWithPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                                 @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                                                 @RequestParam("userId") @ApiParam(value = "用户ID", required = true, type = "integer",example = "1") int userId,
                                                 @RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId){
        return this.iCoinPairChoiceClientService.getListCoinPairChoiceWithPage(pageNum, pageSizeCommon,userId,coinId);
    }

    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoice")
    @GetMapping("/{id}")
    public CoinPairChoice getOneCoinPairChoice(@PathVariable("id")  @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.iCoinPairChoiceClientService.getOneCoinPairChoice(id);
    }

    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoice")
    @PostMapping("/")
    public Result addOneCoinPairChoice(@RequestParam("userId")  @ApiParam(value = "用户id", required = true, type = "integer",example = "1") int userId,
                                       @RequestParam("isStrategy")  @ApiParam(value = "是否开始策略", required = true, type = "integer",example = "1") int isStrategy,
                                       @RequestParam("coinPairId")  @ApiParam(value = "货币对id", required = true, type = "integer",example = "1") int coinPairId){
        return this.iCoinPairChoiceClientService.addOneCoinPairChoice(userId, isStrategy, coinPairId);
    }

    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoice")
    @PutMapping("/")
    public Result updateCoinPairChoice(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") CoinPairChoice coinPairChoice){
        return this.iCoinPairChoiceClientService.updateCoinPairChoice(coinPairChoice);
    }

    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoice")
    @DeleteMapping("/{id}")
    public Result deleteOneCoinPairChoice(@PathVariable("id") @ApiParam(value = "自选币 ID", required = true, type = "integer",example = "1") int id){
        return this.iCoinPairChoiceClientService.deleteOneCoinPairChoice(id);
    }
}
