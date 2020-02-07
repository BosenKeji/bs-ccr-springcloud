package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeCustomEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * add cache by xivin
 * 单表
 */

/**
 * @Author CAJR
 * @create 2019/7/18 17:35
 */
@RestController
@RequestMapping("/coin_pair_choice_attribute_custom")
@Validated
@Api(tags = "CoinPairChoiceAttributeCustom 自选货币自定义属性接口",value = "自选货币自定义属性相关的REST接口")
public class CoinPairChoiceAttributeCustomController {
    @Resource
    CoinPairChoiceAttributeCustomService coinPairChoiceAttributeCustomService;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Resource
    DiscoveryClient client;

    @ApiOperation(value = "获取交易参数接口",httpMethod = "GET" ,nickname = "getOneCoinPairChoiceAttributeCustomByCoinPairChoiceID")
    @GetMapping("/{coinPairChoiceId}")
    public CoinPairChoiceAttributeCustom getByCoinPairChoiceID(@PathVariable("coinPairChoiceId") @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPairChoiceId){
        return this.coinPairChoiceAttributeCustomService.getByCoinPartnerChoiceId(coinPairChoiceId);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "设置交易参数接口",httpMethod = "POST",nickname = "settingParameters")
    @PostMapping("/")
    public Result settingParameters(@RequestBody @Valid @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom){

        if (this.coinPairChoiceService.get(coinPairChoiceAttributeCustom.getCoinPairChoiceId()) == null){
            return new Result<>(null,"自选币不存在");
        }
        if (this.coinPairChoiceAttributeCustomService.checkByCoinPartnerChoiceId(coinPairChoiceAttributeCustom.getCoinPairChoiceId()).get() >=1){
            coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            Optional<Integer> result = this.coinPairChoiceAttributeCustomService.updateByCoinPairChoiceId(coinPairChoiceAttributeCustom);
            if (result.get() > 0){
                return new Result<>(result,"自选币自定义属性存在并更新！");
            }else {
                return new Result<>(result,"自选币自定义属性存在,更新失败！");
            }
        }

        coinPairChoiceAttributeCustom.setStatus(1);
        coinPairChoiceAttributeCustom.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoiceAttributeCustom.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairChoiceAttributeCustomService.add(coinPairChoiceAttributeCustom));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "更新自选货币自定义属性接口",httpMethod = "PUT",nickname = "editCoinPairChoiceAttributeCustom")
    @PutMapping("/")
    public Result update(@RequestBody  @ApiParam(value = "自选币自定义属性实体", required = true, type = "string") CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom){

        if (this.coinPairChoiceService.get(coinPairChoiceAttributeCustom.getCoinPairChoiceId()) == null){
            return new Result<>(null,"自选币不存在");
        }
        if (this.coinPairChoiceAttributeCustomService.get(coinPairChoiceAttributeCustom.getId()) == null){
            return new Result<>(null,"自选币自定义属性不存在");
        }

        return new Result<>(this.coinPairChoiceAttributeCustomService.update(coinPairChoiceAttributeCustom));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "删除自选货币自定义属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoiceAttributeCustomByCoinPartnerChoiceId")
    @DeleteMapping("/{coinPartnerChoiceId}")
    public Result delete(@PathVariable("coinPartnerChoiceId") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int coinPartnerChoiceId){

        if (this.coinPairChoiceAttributeCustomService.checkByCoinPartnerChoiceId(coinPartnerChoiceId).get() < 1){
            return new Result<>(null,"自选币自定义属性不存在");
        }

        return new Result<>(this.coinPairChoiceAttributeCustomService.deleteByCoinPairChoiceId(coinPartnerChoiceId));
    }

    @ApiOperation(value = "批量设置自选币开仓价",httpMethod = "PUT",nickname = "batchSettingFirstOpenPrice")
    @PutMapping("/setting_first_open_price")
    public Result batchSettingFirstOpenPrice(@RequestParam("coinPairIdAndOpenPrice")  @ApiParam(value = "自选币id与其开仓价组合字符串，例如（1-0.1230,888-11.2350,...,889-12.1234）", required = true, type = "string") String coinPairIdAndOpenPrice,
                                             @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        String[] coinPairIdAndOpenPriceStrings = coinPairIdAndOpenPrice.split(",");
        Map<Integer, Double> coinPairIdAndOpenPriceMap = new HashMap<>(16);
        if (coinPairIdAndOpenPriceStrings.length <= 0){
            return new Result<>("自选币id与其开仓价组合字符串格式错误或为空！");
        }
        for (String coinPairIdAndOpenPriceString : coinPairIdAndOpenPriceStrings){
            int index = coinPairIdAndOpenPriceString.indexOf("-");
            int end = coinPairIdAndOpenPriceString.length();
            Integer coinPairId = Integer.valueOf(coinPairIdAndOpenPriceString.substring(0,index));
            Double firstOpenPrice = Double.valueOf(coinPairIdAndOpenPriceString.substring(index+1, end));
            coinPairIdAndOpenPriceMap.put(coinPairId, firstOpenPrice);
        }

        return new Result<>(this.coinPairChoiceAttributeCustomService.batchSetFirstOpenPrice(coinPairIdAndOpenPriceMap,tradePlatformApiBindProductComboId));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
