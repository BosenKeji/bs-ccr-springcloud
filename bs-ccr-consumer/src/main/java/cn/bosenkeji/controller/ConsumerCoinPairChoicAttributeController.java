package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoicAttributeClientService;
import cn.bosenkeji.vo.CoinPairChoicAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author CAJR
 * @create 2019/7/22 16:34
 */
@RestController
@RequestMapping("/consumer/coinpairchoicattribute")
@Api(tags = "CoinPairChoicAttribute 自选货币属性接口",value = "自选货币属性相关功能 Rest接口")
public class ConsumerCoinPairChoicAttributeController {
    @Resource
    ICoinPairChoicAttributeClientService iCoinPairChoicAttributeClientService;


    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET",nickname = "getOneCoinPairChoicAttributeByCoinPatnerChoicID")
    @GetMapping("/{coinPartnerChoicId}")
    public CoinPairChoicAttribute getOneCoinPairChoicAttributeByCoinPatnerChoicID(@PathVariable("coinPartnerChoicId") @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoicId){
        return this.iCoinPairChoicAttributeClientService.getCoinPairChoicAttributeByCoinPartnerChoicID(coinPartnerChoicId);
    }


    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST",nickname = "addOneCoinPairChoicAttribute")
    @PostMapping("/")
    public boolean addOneCoinPairChoicAttribute(@ApiParam(value = "多选框获取多个自选币的id 数组 多选框命名应为:'oinPartnerChoicId'", required = true, type = "string") HttpServletRequest request,
                                                @RequestParam("lever") @ApiParam(value = "策略倍数'", required = true, type = "integer" ,example = "1") int lever,
                                                @RequestParam("money") @ApiParam(value = "预算'", required = true, type = "integer" ,example = "1") int money ,
                                                @RequestParam("isCustom") @ApiParam(value = "是否为自定义属性'", required = true, type = "integer" ,example = "1") int isCustom){
        //获取自选币id字符串数组
        String [] coinPairChoicIdstr=request.getParameterValues("oinPartnerChoicId");
        int[] coinPairChoicIds=new int[coinPairChoicIdstr.length];
        for (int i=0;i<coinPairChoicIdstr.length;i++){
            coinPairChoicIds[i]=Integer.parseInt(coinPairChoicIdstr[i]);
        }

        return this.iCoinPairChoicAttributeClientService.addOneCoinPairChoicAttribute(coinPairChoicIds,lever,money,isCustom);

    }

    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT",nickname = "updateCoinPairChoicAttribute")
    @PutMapping("/")
    public boolean updateCoinPairChoicAttribute(@RequestBody @ApiParam(value = "自选币属性实体'", required = true, type = "string" ) CoinPairChoicAttribute coinPairChoicAttribute){
       return this.iCoinPairChoicAttributeClientService.updateCoinPairChoicAttribute(coinPairChoicAttribute);
    }


    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoicAttributeByCoinPartnerChoicId")
    @DeleteMapping("/{coinPartnerChoicId}")
    public boolean deleteOneCoinPairChoicAttributeByCoinPartnerChoicId(@PathVariable("coinPartnerChoicId") @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoicId){
        return this.iCoinPairChoicAttributeClientService.deleteOneCoinPairChoicAttribute(coinPartnerChoicId);
    }
}
