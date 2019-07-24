package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinPairChoiceAttributeClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 16:34
 */
@RestController
@RequestMapping("/consumer/coin_pair_choice_attribute")
@Api(tags = "CoinPairChoiceAttribute 自选货币属性接口",value = "自选货币属性相关功能 Rest接口")
public class ConsumerCoinPairChoiceAttributeController {
    @Resource
    ICoinPairChoiceAttributeClientService iCoinPairChoiceAttributeClientService;


    @ApiOperation(value = "获取单个自选货币属性接口",httpMethod = "GET",nickname = "getOneCoinPairChoiceAttributeByCoinPartnerChoiceID")
    @GetMapping("/{coinPartnerChoiceId}")
    public CoinPairChoiceAttribute getOneCoinPairChoiceAttributeByCoinPartnerChoiceID(@PathVariable("coinPartnerChoiceId") @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoiceId){
        return this.iCoinPairChoiceAttributeClientService.getCoinPairChoiceAttributeByCoinPartnerChoiceID(coinPartnerChoiceId);
    }


    @ApiOperation(value = "添加自选货币属性接口",httpMethod = "POST",nickname = "addOneCoinPairChoiceAttribute")
    @PostMapping("/")
    public Result addOneCoinPairChoiceAttribute(@ApiParam(value = "多选框获取多个自选币的id 数组 多选框命名应为:'oinPartnerChoiceId'", required = true, type = "string") HttpServletRequest request,
                                                @RequestParam("lever") @ApiParam(value = "策略倍数'", required = true, type = "integer" ,example = "1") int lever,
                                                @RequestParam("money") @ApiParam(value = "预算'", required = true, type = "integer" ,example = "1") int money ,
                                                @RequestParam("isCustom") @ApiParam(value = "是否为自定义属性'", required = true, type = "integer" ,example = "1") int isCustom){
        //获取自选币id字符串数组
        String [] coinPairChoiceIdStr=request.getParameterValues("oinPartnerChoiceId");
        int[] coinPairChoiceIds=new int[coinPairChoiceIdStr.length];
        for (int i=0;i<coinPairChoiceIdStr.length;i++){
            coinPairChoiceIds[i]=Integer.parseInt(coinPairChoiceIdStr[i]);
        }

        return this.iCoinPairChoiceAttributeClientService.addOneCoinPairChoiceAttribute(coinPairChoiceIds,lever,money,isCustom);

    }

    @ApiOperation(value = "更新自选货币属性接口",httpMethod = "PUT",nickname = "updateCoinPairChoiceAttribute")
    @PutMapping("/")
    public Result updateCoinPairChoiceAttribute(@RequestBody @ApiParam(value = "自选币属性实体'", required = true, type = "string" ) CoinPairChoiceAttribute coinPairChoiceAttribute){
       return this.iCoinPairChoiceAttributeClientService.updateCoinPairChoiceAttribute(coinPairChoiceAttribute);
    }


    @ApiOperation(value = "删除自选货币属性接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoiceAttributeByCoinPartnerChoiceId")
    @DeleteMapping("/{coinPartnerChoiceId}")
    public Result deleteOneCoinPairChoiceAttributeByCoinPartnerChoiceId(@PathVariable("coinPartnerChoiceId") @ApiParam(value = "自选币ID'", required = true, type = "integer" ,example = "1") int coinPartnerChoiceId){
        return this.iCoinPairChoiceAttributeClientService.deleteOneCoinPairChoiceAttribute(coinPartnerChoiceId);
    }
}
