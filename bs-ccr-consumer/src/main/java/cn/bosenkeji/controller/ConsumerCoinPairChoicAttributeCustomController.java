package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoicAttributeCustomClientService;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
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
@RequestMapping("/consumer/coinpairchoicattributecustom")
@Api(tags = "CoinPairChoicAttributeCustom 自选货币自定义属性接口",value = "自选货币自定义属性相关的REST接口")
public class ConsumerCoinPairChoicAttributeCustomController {

    @Resource
    ICoinPairChoicAttributeCustomClientService iCoinPairChoicAttributeCustomClientService;

    @ApiOperation(value = "获取交易参数接口",httpMethod = "GET" ,nickname = "getOneCoinPairChoicAttributeCustomByCoinPairChoicID")
    @GetMapping("/{coinPairChoicId}")
    public CoinPairChoicAttributeCustom getOneCoinPairChoicAttributeCustomByCoinPartnerChoicID(@PathVariable("coinPairChoicId") @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoicId){
        return this.iCoinPairChoicAttributeCustomClientService.getCoinPairChoicAttributeCustomByCoinPairChoicId(coinPairChoicId);
    }

    @ApiOperation(value = "设置交易参数接口",httpMethod = "POST",nickname = "settingParameters")
    @PostMapping("/")
    public boolean settingParameters(@RequestBody @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoicAttributeCustom coinPairChoicAttributeCustom){
        return this.iCoinPairChoicAttributeCustomClientService.settingParameters(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "编辑更新自选货币自定义属性接口",httpMethod = "PUT",nickname = "editCoinPairChoicAttributeCustom")
    @PutMapping("/")
    public boolean updateCoinPairChoicAttributeCustom(@RequestBody @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoicAttributeCustom coinPairChoicAttributeCustom){
        return this.iCoinPairChoicAttributeCustomClientService.updateCoinPairChoicAttributeCustom(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "删除自选货币自定义属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoicAttributeCustomByCoinPartnerChoicId")
    @DeleteMapping("/{coinPairChoicId}")
    public boolean  deleteOneCoinPairChoicAttributeCustom(@PathVariable("coinPairChoicId") @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoicId){
        return this.iCoinPairChoicAttributeCustomClientService.deleteOneCoinPairChoicAttributeCustomByCoinPairChoicId(coinPairChoicId);
    }
}
