package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicAttributeEnum;
import cn.bosenkeji.service.CoinPairChoicAttributeService;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import cn.bosenkeji.vo.StrategyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
    public boolean add(HttpServletRequest request,@RequestParam("lever") int lever,@RequestParam("money") int money ,@RequestParam("is_custom") int is_custom){
        //获取自选币id字符串数组
        String [] coinPairChoicIdstr=request.getParameterValues("oinPartnerChoicId");
        int[] coinPairChoicIds=new int[coinPairChoicIdstr.length];
        for (int i=0;i<coinPairChoicIdstr.length;i++){
            coinPairChoicIds[i]=Integer.parseInt(coinPairChoicIdstr[i]);
            System.out.println("coinPairChoicIds[i] = " + coinPairChoicIds[i]);
        }
        System.out.println("coinPairChoicIds = " + coinPairChoicIds.length);

        if (coinPairChoicIds.length == 0){
            return false;
        }

        int expectMoney=(money*lever)/coinPairChoicIds.length;

        for (int i=0;i<coinPairChoicIds.length;i++){
            CoinPairChoicAttribute coinPairChoicAttribute=getByCoinPartnerChoicId(coinPairChoicIds[i]);
            if (coinPairChoicAttribute!= null){
                coinPairChoicAttribute.setExpectMoney(expectMoney);
                coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                update(coinPairChoicAttribute);
            }else {
                CoinPairChoicAttribute coinPairChoicAttribute1 = new CoinPairChoicAttribute();
                coinPairChoicAttribute1.setCoinPartnerChoicId(coinPairChoicIds[i]);
                coinPairChoicAttribute1.setIsCustom(is_custom);
                coinPairChoicAttribute1.setExpectMoney(expectMoney);
                coinPairChoicAttribute1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                coinPairChoicAttribute1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                coinPairChoicAttributeService.add(coinPairChoicAttribute1);
            }
        }

        return true;

    }
    public CoinPairChoicAttribute getByCoinPartnerChoicId(int coinPartnerChoicId){
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
