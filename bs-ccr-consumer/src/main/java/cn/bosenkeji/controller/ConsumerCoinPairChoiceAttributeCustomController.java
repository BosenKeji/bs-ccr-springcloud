package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoiceAttributeCustomClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/coin_pair_choice_attribute_custom")
@Api(tags = "CoinPairChoiceAttributeCustom 自选货币自定义属性接口",value = "自选货币自定义属性相关的REST接口")
public class ConsumerCoinPairChoiceAttributeCustomController {

    @Resource
    ICoinPairChoiceAttributeCustomClientService iCoinPairChoiceAttributeCustomClientService;

    @ApiOperation(value = "获取交易参数接口",httpMethod = "GET" ,nickname = "getOneCoinPairChoiceAttributeCustomByCoinPairChoiceID")
    @GetMapping("/{coinPairChoiceId}")
    public CoinPairChoiceAttributeCustom getOneCoinPairChoiceAttributeCustomByCoinPartnerChoiceID(@PathVariable("coinPairChoiceId") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoiceId){
        return this.iCoinPairChoiceAttributeCustomClientService.getCoinPairChoiceAttributeCustomByCoinPairChoiceId(coinPairChoiceId);
    }

    @ApiOperation(value = "设置交易参数接口",httpMethod = "POST",nickname = "settingParameters")
    @PostMapping("/")
    public Result settingParameters(@RequestBody @ApiParam(value = "自选币自定义属性实体", required = true, type = "string")@NotNull CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom){
        return this.iCoinPairChoiceAttributeCustomClientService.settingParameters(coinPairChoiceAttributeCustom);
    }

    @ApiOperation(value = "编辑更新自选货币自定义属性接口",httpMethod = "PUT",nickname = "editCoinPairChoiceAttributeCustom")
    @PutMapping("/")
    public Result updateCoinPairChoiceAttributeCustom(@RequestBody @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") @NotNull CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom){
        return this.iCoinPairChoiceAttributeCustomClientService.updateCoinPairChoiceAttributeCustom(coinPairChoiceAttributeCustom);
    }

    @ApiOperation(value = "删除自选货币自定义属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoiceAttributeCustomByCoinPartnerChoiceId")
    @DeleteMapping("/{coinPairChoiceId}")
    public Result deleteOneCoinPairChoiceAttributeCustom(@PathVariable("coinPairChoiceId")@Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoiceId){
        return this.iCoinPairChoiceAttributeCustomClientService.deleteOneCoinPairChoiceAttributeCustomByCoinPairChoiceId(coinPairChoiceId);
    }

    @ApiOperation(value = "批量设置自选币开仓价",httpMethod = "PUT",nickname = "batchSettingFirstOpenPrice")
    @PutMapping("/setting_first_open_price")
    public Result batchSettingFirstOpenPrice(@RequestParam("coinPairIdAndOpenPrice") @NotNull @ApiParam(value = "自选币id与其开仓价组合字符串，例如（1-0.1230,888-11.2350,...,889-12.1234）", required = true, type = "string") String coinPairIdAndOpenPrice,
                                             @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        return this.iCoinPairChoiceAttributeCustomClientService.batchSettingFirstOpenPrice(coinPairIdAndOpenPrice, tradePlatformApiBindProductComboId);
    }
    }
