package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceEnum;
import cn.bosenkeji.handler.CustomErrorResponse;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * add cache by xivin
 * 与coinPair coinPairChoiceAttribute coinPairChoiceAttributeCustom 有关联
 */

/**
 * @Author CAJR
 * @create 2019/7/17 16:06
 */
@RestController
@RequestMapping("/coin_pair_choice")
@Validated
@Api(tags = "CoinPairChoice 自选货币接口",value = "自选货币相关功能 rest接口")
@CacheConfig(cacheNames = "ccr:coinPairChoice")
public class CoinPairChoiceController {

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Resource
    ICoinPairClientService iCoinPairClientService;

    @Resource
    DiscoveryClient client;


    @Cacheable(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,key = "#userId+'-'+#coinId+'-'+#pageNum+'-'+#pageSizeCommon")
    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoiceWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                         @RequestParam("userId") @ApiParam(value = "用户ID", required = true, type = "integer",example = "1") int userId,
                         @RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId){
        return this.coinPairChoiceService.listByPage(pageNum,pageSizeCommon,userId,coinId);
    }

    @ApiOperation(value = "检查自选币",httpMethod = "GET",nickname = "checkExistByCoinPartnerIdAndUserId")
    @GetMapping("/check_coin_pair_choice")
    public Result checkExistByCoinPairIdAndUserId(@RequestParam("coinPairName")   @ApiParam(value = "货币对Name", required = true, type = "String") String coinPairName,
                                                  @RequestParam("userId") @Min(1)  @ApiParam(value = "用户id", required = true, type = "integer",example = "1") int userId){

        if (this.coinPairChoiceService.checkExistByCoinPartnerNameAndUserId(coinPairName,userId).get() <= 0){
            Result<CoinPair> result = new Result<>();
            CoinPair coinPair = this.iCoinPairClientService.getCoinPairByName(coinPairName);

            result.setData(coinPair);
            return result;
        }else{
            return new Result<>(this.coinPairChoiceService.checkExistByCoinPartnerNameAndUserId(coinPairName,userId));
        }

    }

    @Cacheable(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,key = "#id",unless = "#result == null")
    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoice")
    @GetMapping("/{id}")
    public CoinPairChoice get(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairChoiceService.get(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoice")
    @PostMapping("/")
    public Result add(@RequestParam("userId") @Min(1)  @ApiParam(value = "用户id", required = true, type = "integer",example = "1") int userId,
                      @RequestParam("isStrategy")  @ApiParam(value = "策略状态", required = true, type = "integer",example = "1") int isStrategy,
                      @RequestParam("coinPairId") @Min(1)  @ApiParam(value = "货币对id", required = true, type = "integer",example = "1") int coinPairId){
        if (this.iCoinPairClientService.getCoinPair(coinPairId) == null){
            return new Result<>(null,"货币对不存在，添加自选币失败");
        }
        if (this.coinPairChoiceService.checkExistByCoinPartnerIdAndUserId(coinPairId,userId).get() >= 1){
            return new Result<>(null,"自选币已存在");
        }

        CoinPairChoice coinPairChoice =new CoinPairChoice();
        coinPairChoice.setUserId(userId);
        coinPairChoice.setCoinPartnerId(coinPairId);
        coinPairChoice.setStatus(1);
        coinPairChoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        coinPairChoice.setIsStart(isStrategy);


        return new Result<>(this.coinPairChoiceService.add(coinPairChoice));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,key = "#coinPairChoice.id"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoice")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") CoinPairChoice coinPairChoice){
        if (this.coinPairChoiceService.get(coinPairChoice.getId()) == null){
            return new Result<>(null,"自选币不存在");
        }

        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairChoiceService.update(coinPairChoice));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoice")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        if (this.coinPairChoiceService.get(id) == null){
            return new Result<>(null,"自选币不存在");
        }

        return new Result<>(this.coinPairChoiceService.delete(id));
    }

    //删除多个如何保证缓存同步呢？
    @Caching(
           evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "批量删除自选货币接口",httpMethod = "DELETE",nickname = "batchDeleteOneCoinPairChoice")
    @DeleteMapping("/batch")
    public Result batchDelete(@RequestParam("coinPairChoiceIds") @ApiParam(value = "自选币ID字符串 ", required = true, type = "string") String coinPairChoiceIds){
        return new Result<>(this.coinPairChoiceService.batchDelete(coinPairChoiceIds));
    }

    
    
    /**
     * @Author hjh
     * @Description 获取所有用户自选货币对交易信息
     * @Date 2019-07-29
     * @return list
     **/
    @GetMapping("/all")
    @ApiIgnore
    public List listCoinPairChoice() {
        return coinPairChoiceService.findAll();
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
