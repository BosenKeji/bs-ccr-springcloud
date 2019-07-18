package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairCoinEnum;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.vo.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 12:14
 */
@RestController
@RequestMapping("/coinpaircoin")
@Api(value = "货币对货币接口")
public class CoinPairCoinController {

    @Resource
    DiscoveryClient discoveryClient;

    @Resource
    CoinPairCoinService coinPairCoinService;


    @ApiOperation(value = "获取货币对货币列表接口",httpMethod = "GET")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairCoinService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币对货币列表接口",httpMethod = "GET")
    @GetMapping("/{id}")
    public CoinPairCoin get(@PathVariable("id") @Min(1) int id){
        return this.coinPairCoinService.get(id).orElseThrow(()-> new NotFoundException(CoinPairCoinEnum.NAME));
    }

    @ApiOperation(value = "添加货币对货币接口",httpMethod = "POST")
    @PostMapping("/")
    public boolean add(@RequestBody CoinPairCoin coinPairCoin){
        coinPairCoin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairCoinService.add(coinPairCoin);
    }

    @ApiOperation(value = "更新货币对货币接口",httpMethod = "PUT")
    @PutMapping("/")
    public boolean update(@RequestBody CoinPairCoin coinPairCoin){
        coinPairCoin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairCoinService.update(coinPairCoin);
    }

    @ApiOperation(value = "删除货币对货币接口",httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") int id){
        return this.coinPairCoinService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover(){
        return this.discoveryClient;
    }

}
