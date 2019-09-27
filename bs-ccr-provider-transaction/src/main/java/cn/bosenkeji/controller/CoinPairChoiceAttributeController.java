package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeCustomEnum;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeEnum;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.service.CoinPairChoiceAttributeService;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategyOther;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 10:57
 */
@RestController
@RequestMapping("/coin_pair_choice_attribute")
@Validated
@Api(tags = "CoinPairChoiceAttribute 自选货币属性接口",value = "自选货币属性相关功能 Rest接口")
public class CoinPairChoiceAttributeController {

    @Resource
    CoinPairChoiceAttributeService coinPairChoiceAttributeService;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Resource
    DiscoveryClient client;


    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET",nickname = "getOneCoinPairChoiceAttributeByCoinPartnerChoiceID")
    @GetMapping("/{coinPartnerChoiceId}")
    public CoinPairChoiceAttribute get(@PathVariable("coinPartnerChoiceId") @Min(1) @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoiceId){
        return this.coinPairChoiceAttributeService.get(coinPartnerChoiceId);
    }

    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST",nickname = "addOneCoinPairChoiceAttribute")
    @PostMapping("/")
    public Result add(@RequestParam(value = "coinPairChoiceIdStr") @ApiParam(value = "多选框获取多个自选币的id 字符串 ", required = true, type = "string") String coinPairChoiceIdStr,
                      @RequestParam("strategyId") @Min(1) @ApiParam(value = "策略id'", required = true, type = "integer" ,example = "1") int strategyId,
                      @RequestParam("money") @ApiParam(value = "预算'", required = true, type = "integer" ,example = "1") int money ,
                      @RequestParam("isCustom") @ApiParam(value = "是否为自定义属性'", required = true, type = "integer" ,example = "1") int isCustom){
        if (coinPairChoiceIdStr.length() == 0){
            return new Result<>(-1,"fail");
        }
        return new Result<>(this.coinPairChoiceAttributeService.setting(coinPairChoiceIdStr, strategyId, money, isCustom));
    }


    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT",nickname = "updateCoinPairChoiceAttribute")
    @PutMapping("/")
    public Result update(@RequestBody  @ApiParam(value = "自选币属性实体'", required = true, type = "string" ) CoinPairChoiceAttribute coinPairChoiceAttribute){
        if (this.coinPairChoiceAttributeService.get(coinPairChoiceAttribute.getId()) == null){
            return new Result<>(null,"自选币属性不存在");
        }
        coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairChoiceAttributeService.update(coinPairChoiceAttribute));
    }

    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoiceAttributeByCoinPartnerChoiceId")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "自选币属性ID'", required = true, type = "integer" ,example = "1") int id){
        if (this.coinPairChoiceAttributeService.get(id) == null){
            return new Result<>(-1,"自选币属性不存在");
        }
        return new Result<>(this.coinPairChoiceAttributeService.delete(id));
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
