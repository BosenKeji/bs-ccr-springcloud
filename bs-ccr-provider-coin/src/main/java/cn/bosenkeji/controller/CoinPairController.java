package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairEnum;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.vo.CoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 11:45
 */
@RestController
@RequestMapping("/coinpair")
@Validated
@Api(value = "货币对接口")
public class CoinPairController {

    @Resource
    CoinPairService coinPairService;

    @Resource
    DiscoveryClient discoveryClient;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取货币对列表接口")
    @GetMapping("/")
    public PageInfo list(){
        return this.coinPairService.listByPage(0,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币对接口")
    @GetMapping("/{id}")
    public CoinPair get(@PathVariable("id") @Min(1) int id){
        return this.coinPairService.get(id).orElseThrow(()->new NotFoundException(CoinPairEnum.NAME));
    }

    @ApiOperation(value = "添加单个货币对接口")
    @PostMapping("/")
    public boolean add(@RequestBody CoinPair coinPair){
        return this.coinPairService.add(coinPair);
    }

    @ApiOperation(value = "更新单个货币对接口")
    @PutMapping("/")
    public boolean update(@RequestBody CoinPair coinPair){
        return this.coinPairService.update(coinPair);
    }

    @ApiOperation(value = "删除单个货币对接口")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinPairService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }
}
