package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeCustomEnum;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeEnum;
import cn.bosenkeji.service.CoinPairChoiceAttributeCustomService;
import cn.bosenkeji.service.CoinPairChoiceAttributeService;
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
    IStrategyService iStrategyService;

    @Resource
    CoinPairChoiceAttributeCustomService coinPairChoiceAttributeCustomService;

    @Resource
    DiscoveryClient client;


    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET",nickname = "getOneCoinPairChoiceAttributeByCoinPartnerChoiceID")
    @GetMapping("/{coinPartnerChoiceId}")
    public CoinPairChoiceAttribute get(@PathVariable("coinPartnerChoiceId") @Min(1) @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoiceId){
        return this.coinPairChoiceAttributeService.get(coinPartnerChoiceId).orElseThrow(()->new NotFoundException(CoinPairChoiceAttributeEnum.NAME));
    }

    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST",nickname = "addOneCoinPairChoiceAttribute")
    @PostMapping("/")
    public Result add(@RequestParam(value = "coinPairChoiceIdStr") @ApiParam(value = "多选框获取多个自选币的id 字符串 ", required = true, type = "string") String coinPairChoiceIdStr,
                      @RequestParam("strategyId") @Min(1) @ApiParam(value = "策略id'", required = true, type = "integer" ,example = "1") int strategyId,
                      @RequestParam("money") @ApiParam(value = "预算'", required = true, type = "integer" ,example = "1") int money ,
                      @RequestParam("isCustom") @ApiParam(value = "是否为自定义属性'", required = true, type = "integer" ,example = "1") int isCustom){
        //字符串切割
        String [] coinPairChoiceIdStrings = coinPairChoiceIdStr.split(",");

        int[] coinPairChoiceIds=new int[coinPairChoiceIdStrings.length];
        for (int i=0;i<coinPairChoiceIdStrings.length;i++){
            coinPairChoiceIds[i]=Integer.parseInt(coinPairChoiceIdStrings[i]);
        }

        if (coinPairChoiceIds.length == 0){
            return new Result<>(0,"fail");
        }

        /*根据strategyId查询StrategyOther*/
        StrategyOther strategyOther = this.iStrategyService.getStrategy(strategyId);

        int lever = strategyOther.getLever();
        int expectMoney=(money*lever)/coinPairChoiceIds.length;


        CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = new CoinPairChoiceAttributeCustom();
        if (strategyOther.getIsStopProfitTrace()!=0){
            coinPairChoiceAttributeCustom.setStopProfitType(1);
        }
        coinPairChoiceAttributeCustom.setStopProfitTraceTriggerRate(strategyOther.getStopProfitTraceTriggerRate());
        coinPairChoiceAttributeCustom.setStopProfitTraceDropRate(strategyOther.getStopProfitTraceDropRate());
        coinPairChoiceAttributeCustom.setStatus(1);

        for (int coinPairChoiceId : coinPairChoiceIds){
            CoinPairChoiceAttribute coinPairChoiceAttribute =getByCoinPartnerChoiceId(coinPairChoiceId);
            coinPairChoiceAttributeCustom.setCoinPartnerChoiceId(coinPairChoiceId);
            System.out.println(coinPairChoiceAttributeCustom.toString());

            /* 数据库已存在的就直接更新其预算和更新时间*/
            if (coinPairChoiceAttribute != null){
                coinPairChoiceAttribute.setExpectMoney(expectMoney);
                coinPairChoiceAttribute.setStrategyId(strategyId);
                coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                /*更新已有自选币的属性*/
                this.coinPairChoiceAttributeService.update(coinPairChoiceAttribute)
                        .filter((value)->value>=1)
                        .orElseThrow(()->new UpdateException(CoinPairChoiceAttributeEnum.NAME));
                /*更新已有自选币的自定义的属性*/
                if (this.coinPairChoiceAttributeCustomService.getByCoinPartnerChoiceId(coinPairChoiceId).isPresent()){
                    this.coinPairChoiceAttributeCustomService.updateByCoinPairChoiceId(coinPairChoiceAttributeCustom)
                            .filter((value)->value>=1)
                            .orElseThrow(()->new UpdateException(CoinPairChoiceAttributeCustomEnum.NAME));
                }else {
                    this.coinPairChoiceAttributeCustomService.add(coinPairChoiceAttributeCustom)
                            .filter((value)->value>=1)
                            .orElseThrow(()->new UpdateException(CoinPairChoiceAttributeCustomEnum.NAME));
                }


            }else {
                CoinPairChoiceAttribute coinPairChoiceAttribute1 = new CoinPairChoiceAttribute();
                coinPairChoiceAttribute1.setCoinPartnerChoiceId(coinPairChoiceId);
                coinPairChoiceAttribute1.setStrategyId(strategyId);
                coinPairChoiceAttribute1.setIsCustom(isCustom);
                coinPairChoiceAttribute1.setStatus(1);
                coinPairChoiceAttribute1.setExpectMoney(expectMoney);
                coinPairChoiceAttribute1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                coinPairChoiceAttribute1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                this.coinPairChoiceAttributeService.add(coinPairChoiceAttribute1)
                        .filter((value)->value>=1)
                        .orElseThrow(()->new AddException(CoinPairChoiceAttributeEnum.NAME));

                this.coinPairChoiceAttributeCustomService.add(coinPairChoiceAttributeCustom)
                        .filter((value)->value>=1)
                        .orElseThrow(()->new UpdateException(CoinPairChoiceAttributeCustomEnum.NAME));
            }
        }

        return new Result<>(1,"success");

    }

    /**
     * 根据自选币id来查是否有其数据
     * @param coinPartnerChoiceId 货币对id
     * @return CoinPairChoiceAttribute
     */
    private CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId){
        return this.coinPairChoiceAttributeService.getByCoinPartnerChoiceId(coinPartnerChoiceId);
    }

    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT",nickname = "updateCoinPairChoiceAttribute")
    @PutMapping("/")
    public Result update(@RequestBody  @ApiParam(value = "自选币属性实体'", required = true, type = "string" ) CoinPairChoiceAttribute coinPairChoiceAttribute){
        coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairChoiceAttributeService.update(coinPairChoiceAttribute)
                .filter((value)->value>=1)
                .orElseThrow(()->new UpdateException(CoinPairChoiceAttributeEnum.NAME)));
    }

    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoiceAttributeByCoinPartnerChoiceId")
    @DeleteMapping("/{coinPartnerChoiceId}")
    public Result delete(@PathVariable("coinPartnerChoiceId") @Min(1) @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoiceId){
        return new Result<>(this.coinPairChoiceAttributeService.delete(coinPartnerChoiceId)
                .filter((value)->value>=1)
                .orElseThrow(()->new DeleteException(CoinPairChoiceAttributeEnum.NAME)));
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
