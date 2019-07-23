package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairCoinEnum;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author CAJR
 * @create 2019/7/11 12:14
 */
@RestController
@RequestMapping("/coin_pair_coin")
@Api(tags = "CoinPairCoin 货币对货币相关接口", value = "提供货币对货币相关接口的 Rest API")
public class CoinPairCoinController {

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    CoinPairCoinService coinPairCoinService;


    @ApiOperation(value = "获取货币对货币列表接口",httpMethod = "GET",nickname = "getCoinPairCoinListWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairCoinService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币对货币列表接口",nickname = "getOneCoinPairCoin",httpMethod = "GET",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "{\n" +
                            "  \"id\": 1,\n" +
                            "  \"name\": \"btc\",\n" +
                            "  \"status\": 0,\n" +
                            "  \"createdAt\": \"2019-07-17T19:13:37.000+0000\",\n" +
                            "  \"updatedAt\": \"2019-07-17T19:13:37.000+0000\"\n" +
                            "}"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @GetMapping("/{id}")
    public CoinPairCoin get(@PathVariable("id") @Min(1) @ApiParam(value = "货币对货币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairCoinService.get(id).orElseThrow(()-> new NotFoundException(CoinPairCoinEnum.NAME));
    }

    @ApiOperation(value = "添加货币对货币接口",httpMethod = "POST",nickname = "addOneCoinPairCoin",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "ok"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @PostMapping("/")
    public boolean add(@RequestBody @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){
        coinPairCoin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairCoinService.add(coinPairCoin);
    }

    @ApiOperation(value = "更新货币对货币接口",httpMethod = "PUT",nickname = "updateCoinPairCoin",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "ok"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @PutMapping("/")
    public boolean update(@RequestBody @ApiParam(value = "货币对货币实体", required = true, type = "string") CoinPairCoin coinPairCoin){
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairCoinService.update(coinPairCoin);
    }

    @ApiOperation(value = "删除货币对货币接口",httpMethod = "DELETE",nickname = "deleteCoinPairCoin",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "ok"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @ApiParam(value = "货币对货币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairCoinService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }

}
