package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.TradePlatformCoinPairEnum;
import cn.bosenkeji.service.TradePlatformCoinPairService;
import cn.bosenkeji.vo.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author CAJR
 * @create 2019/7/16 14:55
 */
@RestController
@RequestMapping("/tradeplatformcoinpairs")
@Validated
@Api(value = "平台货币对接口")
public class TradePlatformCoinPairController {

    @Resource
    TradePlatformCoinPairService tradePlatformCoinPairService;

    @Resource
    DiscoveryClient client;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取平台货币对列表接口",httpMethod = "GET")
    @GetMapping("/")
    public PageInfo list(){
        return this.tradePlatformCoinPairService.listByPage(0,pageSizeCommon);
    }

    @ApiOperation(value = "获取平台货币对单个信息接口",httpMethod = "GET")
    @GetMapping("/{id}")
    public TradePlatformCoinPair get(@PathVariable("id") @Min(1) int id){
        return this.tradePlatformCoinPairService.get(id).orElseThrow(()-> new NotFoundException(TradePlatformCoinPairEnum.NAME));
    }

    @ApiOperation(value = "添加平台货币对单个信息接口",httpMethod = "POST")
    @PostMapping("/")
    public boolean add(@RequestBody @NotNull TradePlatformCoinPair tradePlatformCoinPair){
        tradePlatformCoinPair.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformCoinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.tradePlatformCoinPairService.add(tradePlatformCoinPair);
    }

    @ApiOperation(value = "更新单个平台货币对接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody @NotNull TradePlatformCoinPair tradePlatformCoinPair){
        tradePlatformCoinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.tradePlatformCoinPairService.update(tradePlatformCoinPair);
    }

    @ApiOperation(value = "删除单个平台货币对接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) int id){
        return this.tradePlatformCoinPairService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
