package cn.bosenkeji.controller;

import cn.bosenkeji.config.ExceptionConfig;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinEnum;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.Coin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "货币接口")
public class CoinController {

    @Resource
    private CoinService coinService;

    @Resource
    private DiscoveryClient client ;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取货币列表接口", httpMethod = "GET")
    @RequestMapping(value="/")
    public PageInfo list() {
        return this.coinService.listByPage(0,pageSizeCommon) ;
    }

    @ApiOperation(value = "获取单个货币信息列表接口", httpMethod = "GET")
    @RequestMapping(value="/{id}")
    public Coin get( @PathVariable("id")  @Min(value = 1) @ApiParam(value = "币种ID", required = true) int id) {
        return this.coinService.get(id).orElseThrow(()-> new NotFoundException(CoinEnum.NAME)) ;
    }

    @ApiOperation(value = "添加单个货币信息列表接口", httpMethod = "POST")
    @RequestMapping(value="/", method = RequestMethod.POST)
    public boolean add(@RequestBody @NotNull @ApiParam(value = "币种实体属性", required = true) Coin coin) {
        coin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinService.add(coin) ;
    }

    @ApiOperation(value = "更新单个货币信息列表接口", httpMethod = "PUT")
    @RequestMapping(value="/", method = RequestMethod.PUT)
    public boolean put(@RequestBody Coin coin) {
        coin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinService.update(coin) ;
    }

    @ApiOperation(value = "删除单个货币信息列表接口", httpMethod = "DELETE")
    @RequestMapping(value="/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") @ApiParam(value = "币种ID", required = true) int id) {
        return this.coinService.delete(id) ;
    }


    @ApiOperation(value = "发现服务接口")
    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
