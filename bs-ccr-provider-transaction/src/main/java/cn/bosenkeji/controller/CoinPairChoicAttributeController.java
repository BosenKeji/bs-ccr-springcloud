package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicAttributeEnum;
import cn.bosenkeji.service.CoinPairChoicAttributeService;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import cn.bosenkeji.vo.StrategyVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
@Api(tags = "CoinPairChoicAttribute 自选货币属性接口",value = "自选货币属性相关功能 Rest接口")
public class CoinPairChoicAttributeController {

    @Resource
    CoinPairChoicAttributeService coinPairChoicAttributeService;
    @Resource
    DiscoveryClient client;

    @Value("${pageSize.common}")
    private int pageSizeCommon;


    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinPairChoicAttribute get(@PathVariable("id") @Min(1) @ApiParam(value = "自选币属性ID'", required = true, type = "integer" ,example = "1") int id){
        return this.coinPairChoicAttributeService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicAttributeEnum.NAME));
    }

    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST")
    @PostMapping("/")
    public boolean add(@ApiParam(value = "多选框获取多个自选币的id 多选框命名为:'oinPartnerChoicId'", required = true, type = "string") HttpServletRequest request,
                       @RequestParam("lever") @ApiParam(value = "策略倍数'", required = true, type = "integer" ,example = "1") int lever,
                       @RequestParam("money") @ApiParam(value = "预算'", required = true, type = "integer" ,example = "1") int money ,
                       @RequestParam("isCustom") @ApiParam(value = "是否为自定义属性'", required = true, type = "integer" ,example = "1") int isCustom){
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

            /* 数据库已存在的就直接更新其预算和更新时间*/
            if (coinPairChoicAttribute!= null){
                coinPairChoicAttribute.setExpectMoney(expectMoney);
                coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                update(coinPairChoicAttribute);
            }else {
                CoinPairChoicAttribute coinPairChoicAttribute1 = new CoinPairChoicAttribute();
                coinPairChoicAttribute1.setCoinPartnerChoicId(coinPairChoicIds[i]);
                coinPairChoicAttribute1.setIsCustom(isCustom);
                coinPairChoicAttribute1.setExpectMoney(expectMoney);
                coinPairChoicAttribute1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                coinPairChoicAttribute1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                coinPairChoicAttributeService.add(coinPairChoicAttribute1);
            }
        }

        return true;

    }

    /**
     * 根据自选币id来查是否有其数据
     * @param coinPartnerChoicId
     * @return CoinPairChoicAttribute
     */
    private CoinPairChoicAttribute getByCoinPartnerChoicId(int coinPartnerChoicId){
        return this.coinPairChoicAttributeService.getByCoinPartnerChoicId(coinPartnerChoicId);
    }

    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody  @ApiParam(value = "自选币属性实体'", required = true, type = "string" ) CoinPairChoicAttribute coinPairChoicAttribute){
        coinPairChoicAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicAttributeService.update(coinPairChoicAttribute);
    }

    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) @ApiParam(value = "自选币属性ID'", required = true, type = "integer" ,example = "1") int id){
        return this.coinPairChoicAttributeService.delete(id);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
