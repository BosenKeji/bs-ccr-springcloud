package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicAttributeEnum;
import cn.bosenkeji.service.CoinPairChoicAttributeService;
import cn.bosenkeji.vo.CoinPairChoic;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/18 10:57
 */
@RestController
@RequestMapping("/coinpairchoicattribute")
@Validated
@Api(value = "自选货币属性接口")
public class CoinPairChoicAttributeController {

    @Resource
    CoinPairChoicAttributeService coinPairChoicAttributeService;
    @Resource
    DiscoveryClient client;

    @Value("${pageSize.common}")
    private int pageSizeCommon;




    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinPairChoicAttribute get(@PathVariable("id") @Min(1) int id){
        return this.coinPairChoicAttributeService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicAttributeEnum.NAME));
    }

    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST")
    @PostMapping("/")
    public boolean add(@RequestBody @NotNull List<CoinPairChoic> coinPairChoics, @RequestBody @NotNull StrategyVO strategyVO, @RequestBody int money , @RequestBody int is_custom){
        int lever=strategyVO.getLever();
        int expectMoney=(money*lever)/coinPairChoics.size();

        for (CoinPairChoic coinPairChoic:coinPairChoics){

            if (getByCoinPartnerChoicId(coinPairChoic.getId()) != null){
                CoinPairChoicAttribute coinPairChoicAttribute=getByCoinPartnerChoicId(coinPairChoic.getId());
                coinPairChoicAttribute.setExpectMoney(expectMoney);
                coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                return update(coinPairChoicAttribute);
            }

            CoinPairChoicAttribute coinPairChoicAttribute=new CoinPairChoicAttribute();
            coinPairChoicAttribute.setCoinPartnerChoicId(coinPairChoic.getId());
            coinPairChoicAttribute.setIsCustom(is_custom);
            coinPairChoicAttribute.setExpectMoney(expectMoney);
            coinPairChoicAttribute.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            coinPairChoicAttributeService.add(coinPairChoicAttribute);
        }

        return true;

    }
    public CoinPairChoicAttribute getByCoinPartnerChoicId(@PathVariable("id") @Min(1) int coinPartnerChoicId){
        return this.coinPairChoicAttributeService.getByCoinPartnerChoicId(coinPartnerChoicId);
    }

    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody CoinPairChoicAttribute coinPairChoicAttribute){
        coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicAttributeService.update(coinPairChoicAttribute);
    }

    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) int id){
        return this.coinPairChoicAttributeService.delete(id);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
