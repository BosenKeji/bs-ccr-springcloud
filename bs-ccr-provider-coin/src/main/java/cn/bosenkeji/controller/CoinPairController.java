package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairEnum;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 11:45
 */
@RestController
@RequestMapping("/coin_pair")
@Validated
@Api(tags = "CoinPair 货币对相关接口", value = "提供货币对相关接口的 Rest API")
public class CoinPairController {

    @Resource
    CoinPairService coinPairService;

    @Resource
    DiscoveryClient discoveryClient;

    @ApiOperation(value = "获取货币对列表接口",httpMethod = "GET",nickname = "getCoinPairByPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个货币对接口",httpMethod = "GET",nickname = "getOneCoinPair")
    @GetMapping("/{id}")
    public CoinPair get(@PathVariable("id") @Min(1)  @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairService.get(id).orElseThrow(()->new NotFoundException(CoinPairEnum.NAME));
    }

    @ApiOperation(value = "添加单个货币对接口",httpMethod = "POST",nickname = "addOneCoinPair")
    @PostMapping("/")
    public Result add(@RequestBody @Valid @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){
        this.coinPairService.checkExistByName(coinPair.getName())
                .filter((value)->value==0)
                .orElseThrow(()->new AddException(CoinPairEnum.NAME));

        coinPair.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairService.add(coinPair)
                .filter((value)->value>=1)
                .orElseThrow(()->new AddException(CoinPairEnum.NAME)));
    }

    @ApiOperation(value = "更新单个货币对接口",httpMethod = "PUT",nickname = "updateOneCoinPair")
    @PutMapping("/")
    public Result update(@RequestBody @Valid  @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){
        this.coinPairService.checkExistByName(coinPair.getName())
                .filter((value)->value>=1)
                .orElseThrow(()->new UpdateException(CoinPairEnum.NAME));

        coinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairService.update(coinPair)
                .filter((value)->value>=1)
                .orElseThrow(()->new UpdateException(CoinPairEnum.NAME)));
    }

    @ApiOperation(value = "删除单个货币对接口",httpMethod = "DELETE",nickname = "deleteOneCoinPair")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){

        return new Result <>(this.coinPairService.delete(id)
                .filter((value)->value>=1)
                .orElseThrow(()->new DeleteException(CoinPairEnum.NAME)));
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }
}
