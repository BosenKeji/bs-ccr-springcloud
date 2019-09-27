package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 11:45
 */
@RestController
@RequestMapping("/coin_pair")
@Validated
@Api(tags = "CoinPair 货币对相关接口", value = "提供货币对相关接口的 Rest API")
@CacheConfig(cacheNames = "ccr:coinPair")
public class CoinPairController {

    @Resource
    CoinPairService coinPairService;

    @Resource
    DiscoveryClient discoveryClient;


    //@ApiOperation(value = "获取货币对列表接口",httpMethod = "GET",nickname = "getCoinPairByPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairService.listByPage(pageNum,pageSizeCommon);
    }

    //@Cacheable(value = RedisInterface.COIN_PAIR_ID_KEY,key = "#id")
    @ApiOperation(value = "获取单个货币对接口",httpMethod = "GET",nickname = "getOneCoinPair")
    @GetMapping("/{id}")
    public CoinPair get(@PathVariable("id") @Min(1)  @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairService.get(id);
    }

    //@Cacheable(value = RedisInterface.COIN_PAIR_LIST_NAME_KEY,key = "#name")
    @ApiOperation(value = "根据货币对name获取货币对信息接口",httpMethod = "GET",nickname = "getOneCoinPairByName")
    @GetMapping("/by_name/{name}")
    public CoinPair getOneCoinPairByName(@PathVariable("name")   @ApiParam(value = "货币对name", required = true, type = "string") String name){
        return this.coinPairService.getByName(name);
    }

    @ApiOperation(value = "添加单个货币对接口",httpMethod = "POST",nickname = "addOneCoinPair")
    @PostMapping("/")
    public Result add(@RequestBody @Valid @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){

        if (this.coinPairService.checkExistByName(coinPair.getName()).get()>=1){
            return new Result<>(null,"货币对已存在");
        }

        coinPair.setStatus(1);
        coinPair.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairService.add(coinPair));
    }

    @ApiOperation(value = "更新单个货币对接口",httpMethod = "PUT",nickname = "updateOneCoinPair")
    @PutMapping("/")
    public Result update(@RequestBody @Valid  @ApiParam(value = "货币对实体", required = true, type = "String") CoinPair coinPair){
        if (this.coinPairService.get(coinPair.getId()) == null){
            return new Result<>(null,"货币对不存在");
        }
        coinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairService.update(coinPair));
    }

    @ApiOperation(value = "删除单个货币对接口",httpMethod = "DELETE",nickname = "deleteOneCoinPair")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1)  @ApiParam(value = "货币对ID", required = true, type = "integer",example = "1") int id){

        if (this.coinPairService.get(id) == null){
            return new Result<>(null,"货币对不存在");
        }

        return new Result <>(this.coinPairService.delete(id));
    }

    @GetMapping("/section")
    @ApiIgnore
    public List<CoinPair> findSection(@RequestParam("ids") List<Integer> ids){
        return this.coinPairService.listSection(ids);
    }

    @GetMapping("/all")
    @ApiIgnore
    public List<CoinPair> findAll() {
        return coinPairService.list();
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover(){
        return this.discoveryClient;
    }
}
