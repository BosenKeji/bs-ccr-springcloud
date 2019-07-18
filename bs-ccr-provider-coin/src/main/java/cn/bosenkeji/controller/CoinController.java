package cn.bosenkeji.controller;

import cn.bosenkeji.config.ExceptionConfig;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinEnum;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName CoinController
 * @Description 货币
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@RestController
@RequestMapping("/coin")
@Validated
@Api(tags = "Coin 货币相关接口", value = "提供货币相关接口的 Rest API")
public class CoinController {

    @Resource
    private CoinService coinService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取货币列表接口", httpMethod = "GET",
            extensions = {
                @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
                @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                                                                            @ExtensionProperty(name = "mockResult", value = "{\n" +
                                                                                    "  \"total\": 2,\n" +
                                                                                    "  \"list\": [\n" +
                                                                                    "    {\n" +
                                                                                    "      \"id\": 1,\n" +
                                                                                    "      \"name\": \"btc\",\n" +
                                                                                    "      \"status\": 0,\n" +
                                                                                    "      \"createdAt\": \"2019-07-17T19:13:37.000+0000\",\n" +
                                                                                    "      \"updatedAt\": \"2019-07-17T19:13:37.000+0000\"\n" +
                                                                                    "    },\n" +
                                                                                    "    {\n" +
                                                                                    "      \"id\": 2,\n" +
                                                                                    "      \"name\": \"usdt\",\n" +
                                                                                    "      \"status\": 0,\n" +
                                                                                    "      \"createdAt\": \"2019-07-17T19:15:53.000+0000\",\n" +
                                                                                    "      \"updatedAt\": \"2019-07-17T19:15:53.000+0000\"\n" +
                                                                                    "    }\n" +
                                                                                    "  ],\n" +
                                                                                    "  \"pageNum\": 0,\n" +
                                                                                    "  \"pageSize\": 10,\n" +
                                                                                    "  \"size\": 2,\n" +
                                                                                    "  \"startRow\": 1,\n" +
                                                                                    "  \"endRow\": 2,\n" +
                                                                                    "  \"pages\": 1,\n" +
                                                                                    "  \"prePage\": 0,\n" +
                                                                                    "  \"nextPage\": 1,\n" +
                                                                                    "  \"isFirstPage\": false,\n" +
                                                                                    "  \"isLastPage\": false,\n" +
                                                                                    "  \"hasPreviousPage\": false,\n" +
                                                                                    "  \"hasNextPage\": true,\n" +
                                                                                    "  \"navigatePages\": 8,\n" +
                                                                                    "  \"navigatepageNums\": [\n" +
                                                                                    "    1\n" +
                                                                                    "  ],\n" +
                                                                                    "  \"navigateFirstPage\": 1,\n" +
                                                                                    "  \"navigateLastPage\": 1\n" +
                                                                                    "}"),
                                                                            @ExtensionProperty(name = "mockStatusCode", value = "200")
                })
            })
    @RequestMapping(value="/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return this.coinService.listByPage(pageNum,pageSizeCommon) ;
    }

    @ApiOperation(value = "获取单个货币信息列表接口", httpMethod = "GET",extensions = {
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
    @RequestMapping(value="/{id}")
    public Coin get( @PathVariable("id")  @Min(value = 1) @ApiParam(value = "币种ID", required = true, type = "integer") int id) {
        return this.coinService.get(id).orElseThrow(()-> new NotFoundException(CoinEnum.NAME)) ;
    }

    @ApiOperation(value = "添加单个货币信息列表接口", httpMethod = "POST",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "OK"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @RequestMapping(value="/", method = RequestMethod.POST)
    public boolean add(@RequestBody @NotNull @ApiParam(value = "币种实体", required = true, type = "string") Coin coin) {
        coin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinService.add(coin) ;
    }

    @ApiOperation(value = "更新单个货币信息列表接口", httpMethod = "PUT",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "OK"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @RequestMapping(value="/", method = RequestMethod.PUT)
    public boolean put(@RequestBody @ApiParam(value = "币种实体", required = true, type = "string") Coin coin) {
        coin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinService.update(coin) ;
    }

    @ApiOperation(value = "删除单个货币信息列表接口", httpMethod = "DELETE",extensions = {
            @Extension(properties={@ExtensionProperty(name = "x-aliyun-apigateway-paramater-handling", value = "MAPPING")}),
            @Extension(name = "aliyun-apigateway-backend",  properties={@ExtensionProperty(name = "type", value = "MOCK"),
                    @ExtensionProperty(name = "mockResult", value = "OK"),
                    @ExtensionProperty(name = "mockStatusCode", value = "200")
            })
    })
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") @ApiParam(value = "币种ID", required = true, type = "integer") int id) {
        return this.coinService.delete(id) ;
    }

}
