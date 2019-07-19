package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicAttributeCustomEnum;
import cn.bosenkeji.service.CoinPairChoicAttributeCustomService;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "自选货币自定义属性接口")
public class CoinPairChoicAttributeCustomController {
    @Resource
    CoinPairChoicAttributeCustomService coinPairChoicAttributeCustomService;

    @Resource
    DiscoveryClient client;

    @ApiOperation(value = "设置交易参数接口")
    @GetMapping("/{id}")
    public CoinPairChoicAttributeCustom get(@RequestParam("id") @Min(value = 1) int id){
        return this.coinPairChoicAttributeCustomService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicAttributeCustomEnum.NAME));
    }
    @ApiOperation(value = "设置交易参数接口")
    @PostMapping("/setting")
    public boolean settingParameters(HttpServletRequest request){
        int coinPatnerChoicId= Integer.parseInt(request.getParameter("coinPatnerChoicId"));
        int stopProfitType=Integer.parseInt(request.getParameter("stopProfitType"));
        int stopProfitMoney=Integer.parseInt(request.getParameter("stopProfitMoney"));


        CoinPairChoicAttributeCustom coinPairChoicAttributeCustom = new CoinPairChoicAttributeCustom();
        coinPairChoicAttributeCustom.setCoinPartnerChoicId(coinPatnerChoicId);
        coinPairChoicAttributeCustom.setStopProfitMoney(stopProfitMoney);
        coinPairChoicAttributeCustom.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        if (stopProfitType == 1){

            coinPairChoicAttributeCustom.setStopProfitType(stopProfitType);
            coinPairChoicAttributeCustom.setStopProfitTraceTriggerRate(Double.valueOf(request.getParameter("stopProfitTraceTriggerRate")));

            return this.coinPairChoicAttributeCustomService.add(coinPairChoicAttributeCustom);
        }

        if (stopProfitType == 2){

            coinPairChoicAttributeCustom.setStopProfitType(stopProfitType);
            coinPairChoicAttributeCustom.setStopProfitTraceTriggerRate(Double.valueOf(request.getParameter("stopProfitTraceDropRate")));
            return this.coinPairChoicAttributeCustomService.add(coinPairChoicAttributeCustom);
        }

        coinPairChoicAttributeCustom.setStopProfitFixedRate(Double.valueOf(request.getParameter("stopProfitFixedRate")));
        return this.coinPairChoicAttributeCustomService.add(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "更新自选货币自定义属性接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody CoinPairChoicAttributeCustom coinPairChoicAttributeCustom){
        coinPairChoicAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicAttributeCustomService.update(coinPairChoicAttributeCustom);
    }

    @ApiOperation(value = "删除自选货币自定义属性接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) int id){
        return this.coinPairChoicAttributeCustomService.delete(id);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
