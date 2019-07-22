package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicAttributeCustomEnum;
import cn.bosenkeji.service.CoinPairChoicAttributeCustomService;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author CAJR
 * @create 2019/7/18 17:35
 */
@RestController
@RequestMapping("/coinpairchoicattributecustom")
@Validated
@Api(tags = "CoinPairChoicAttributeCustom 自选货币自定义属性接口",value = "自选货币自定义属性相关的REST接口")
public class CoinPairChoicAttributeCustomController {
    @Resource
    CoinPairChoicAttributeCustomService coinPairChoicAttributeCustomService;

    @Resource
    DiscoveryClient client;

    @ApiOperation(value = "获取交易参数接口",httpMethod = "GET" ,nickname = "getOneCoinPairChoicAttributeCustomByCoinPairChoicAttributeCustomID")
    @GetMapping("/{id}")
    public CoinPairChoicAttributeCustom get(@RequestParam("id") @Min(value = 1) @ApiParam(value = "自选币自定义属性ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairChoicAttributeCustomService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicAttributeCustomEnum.NAME));
    }

    @ApiOperation(value = "获取交易参数接口",httpMethod = "GET" ,nickname = "getOneCoinPairChoicAttributeCustomByCoinPairChoicID")
    @GetMapping("/{coinPairChoicId}")
    public CoinPairChoicAttributeCustom getByCoinPairChoicID(@PathVariable("coinPairChoicId") @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoicId){
        return this.coinPairChoicAttributeCustomService.getByCoinPartnerChoicId(coinPairChoicId).orElseThrow(()->new NotFoundException(CoinPairChoicAttributeCustomEnum.NAME));
    }

    @ApiOperation(value = "设置交易参数接口",httpMethod = "POST",nickname = "settingParameters")
    @PostMapping("/setting")
    public boolean settingParameters(@RequestBody @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoicAttributeCustom coinPairChoicAttributeCustom){
        coinPairChoicAttributeCustom.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicAttributeCustomService.add(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "更新自选货币自定义属性接口",httpMethod = "PUT",nickname = "editCoinPairChoicAttributeCustom")
    @PutMapping("/")
    public boolean update(@RequestBody  @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoicAttributeCustom coinPairChoicAttributeCustom){
        int stopProfitType= coinPairChoicAttributeCustom.getStopProfitType();

        if (stopProfitType == 1){
            coinPairChoicAttributeCustom.setStopProfitFixedRate(0.00);
            coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return this.coinPairChoicAttributeCustomService.update(coinPairChoicAttributeCustom);
        }
        else if (stopProfitType == 2){
            coinPairChoicAttributeCustom.setStopProfitTraceTriggerRate(0.00);
            coinPairChoicAttributeCustom.setStopProfitTraceDropRate(0.00);
            coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            return this.coinPairChoicAttributeCustomService.update(coinPairChoicAttributeCustom);
        }

        coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicAttributeCustomService.update(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "删除自选货币自定义属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoicAttributeCustomByCoinPartnerChoicId")
    @DeleteMapping("/{coinPartnerChoicId}")
    public boolean delete(@PathVariable("coinPartnerChoicId") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPartnerChoicId){
        return this.coinPairChoicAttributeCustomService.delete(coinPartnerChoicId);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
