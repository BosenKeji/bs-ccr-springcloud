package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.cache.BatchCacheRemove;
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


    /**
     * 与coinPair coinPairChoiceAttribute coinPairChoiceAttributeCustom 关联查询
     * @param pageNum
     * @param pageSizeCommon
     * @param tradePlatformApiBindProductComboId
     * @param coinId
     * @return
     */

    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoiceWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                         @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                         @RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId){
        return this.coinPairChoiceService.listByPage(pageNum,pageSizeCommon,tradePlatformApiBindProductComboId,coinId);
    }

    /**
     * create by xivin
     * @param tradePlatformApiBindProductComboId
     * @param isStart
     * @return
     */
    @ApiOperation(value = "通过绑定id 和 是否开启策略 获取自选货币列表接口",httpMethod = "GET",nickname = "getByRobotIdAndIsStartCoinPairChoiceList")
    @GetMapping("/by_is_start")
    public List listByIsStart(
                         @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                         @RequestParam("isStart") @ApiParam(value = "是否开启策略", required = true, type = "integer",example = "1") int isStart,
                         @RequestParam("coinId") @ApiParam(value = "货币id",required = true,type = "integer",example = "1") int coinId ){
        return this.coinPairChoiceService.listByRobotIdAndIsStart(tradePlatformApiBindProductComboId,isStart,coinId);
    }

    @ApiOperation(value = "检查自选币",httpMethod = "GET",nickname = "checkExistByCoinPartnerIdAndUserId")
    @GetMapping("/check_coin_pair_choice")
    public Result checkExistByCoinPairIdAndUserId(@RequestParam("coinPairName")   @ApiParam(value = "货币对Name", required = true, type = "String") String coinPairName,
                                                  @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){

        if (this.coinPairChoiceService.checkExistByCoinPartnerNameAndRobotId(coinPairName,tradePlatformApiBindProductComboId).get() <= 0){
            Result<CoinPair> result = new Result<>();
            CoinPair coinPair = this.iCoinPairClientService.getCoinPairByName(coinPairName);

            result.setData(coinPair);
            return result;
        }else{
            return new Result<>(this.coinPairChoiceService.checkExistByCoinPartnerNameAndRobotId(coinPairName,tradePlatformApiBindProductComboId));
        }

    }

    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoice")
    @GetMapping("/{id}")
    public CoinPairChoice get(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairChoiceService.get(id);
    }

    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoice")
    @PostMapping("/")
    public Result add(@RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                      @RequestParam("isStrategy")  @ApiParam(value = "策略状态", required = true, type = "integer",example = "1") int isStrategy,
                      @RequestParam("coinPairId") @Min(1)  @ApiParam(value = "货币对id", required = true, type = "integer",example = "1") int coinPairId){
        if (this.iCoinPairClientService.getCoinPair(coinPairId) == null){
            return new Result<>(null,"货币对不存在，添加自选币失败");
        }
        if (this.coinPairChoiceService.checkExistByCoinPartnerIdAndRobotId(coinPairId,tradePlatformApiBindProductComboId).get() >= 1){
            return new Result<>(null,"自选币已存在");
        }

        CoinPairChoice coinPairChoice =new CoinPairChoice();
//        coinPairChoice.setUserId(userId);
        coinPairChoice.setTradePlatformApiBindProductComboId(tradePlatformApiBindProductComboId);
        coinPairChoice.setCoinPairId(coinPairId);
        coinPairChoice.setStatus(1);
        coinPairChoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoice.setIsStart(isStrategy);


        return new Result<>(this.coinPairChoiceService.add(coinPairChoice));
    }

    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoice")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") CoinPairChoice coinPairChoice){
        CoinPairChoice coinPairChoiceVerification = this.coinPairChoiceService.get(coinPairChoice.getId());
        if (coinPairChoiceVerification == null || coinPairChoiceVerification.getStatus() == 0){
            return new Result<>(null,"自选币不存在或已删除");
        }
        if (coinPairChoiceVerification.getTradePlatformApiBindProductComboId() != coinPairChoice.getTradePlatformApiBindProductComboId()){
            return new Result<>(null,"非法操作，不能编辑其他用户的东西哦");
        }

        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.coinPairChoiceService.update(coinPairChoice));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,key = "#id",condition = "#result.data != null"),
            }
    )
    @BatchCacheRemove(value = "'ccr:coinPairChoice:list::'+#tradePlatformApiBindProductComboId+'-'",condition = "#result.data != null")
    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoice")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id,
                         @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        CoinPairChoice coinPairChoice = this.coinPairChoiceService.get(id);
        if (coinPairChoice == null || coinPairChoice.getStatus() == 0){
            return new Result<>(null,"自选币不存在或已删除");
        }
        if (coinPairChoice.getTradePlatformApiBindProductComboId() != tradePlatformApiBindProductComboId){
            return new Result<>(null,"非法操作，不能删除其他用户的东西哦");
        }

        return new Result<>(this.coinPairChoiceService.delete(id));
    }


    @ApiOperation(value = "批量删除自选货币接口",httpMethod = "DELETE",nickname = "batchDeleteOneCoinPairChoice")
    @DeleteMapping("/batch")
    public Result batchDelete(@RequestParam("coinPairChoiceIds") @ApiParam(value = "自选币ID字符串 ", required = true, type = "string") String coinPairChoiceIds,
                              @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId){
        Optional<Integer> result = this.coinPairChoiceService.batchDelete(coinPairChoiceIds,tradePlatformApiBindProductComboId);
        if (result.get() == 0){
            return new Result<>(null,"批量删除自选币失败！");
        }
        return new Result<>(result);
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

    @ApiOperation(value = "根据计价货币id查询有买卖记录的货币对",httpMethod = "GET",nickname = "recordByCoinId")
    @GetMapping("/record")
    public PageInfo recordByCoinId (@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                                    @RequestParam("tradePlatformApiBindProductComboId") @Min(1)  @ApiParam(value = "🤖️机器人🆔", required = true, type = "integer",example = "1") int tradePlatformApiBindProductComboId,
                                    @RequestParam("coinId") @ApiParam(value = "货币ID", required = true, type = "integer",example = "1") int coinId,
                                    @RequestParam("type") @ApiParam(value = "收益记录（'profit'）还是买入日志（'buy'）", required = true, type = "String") String type){
        return this.coinPairChoiceService.recordByPage(pageNum, pageSizeCommon, tradePlatformApiBindProductComboId, coinId, type);
    }

    @ApiOperation(value = "根据自选货币对id查询有持仓详情",httpMethod = "GET",nickname = "getCoinPairChoicePositionDetails")
    @GetMapping("/position_details")
    public Result getCoinPairChoicePositionDetails(@RequestParam("coinPairChoiceId") @Min(1)  @ApiParam(value = "自选币id", required = true, type = "integer",example = "1") int coinPairChoiceId){
        return new Result<>(this.coinPairChoiceService.getCoinPairChoicePositionDetail(coinPairChoiceId));
    }

    /**
     *  xivinChen
     * @param originalBindId
     * @param newBindId
     * @return
     */
    @PutMapping("/bind_id/")
    public Result<Integer> updateByBindId(@RequestParam("originalBindId") @ApiParam(value = "原来的绑定",required = true,type = "integer",example = "1") int originalBindId,
                                          @RequestParam("newBindId") @ApiParam(value = "新的的绑定",required = true,type = "integer",example = "1") int newBindId) {
        return new Result<>(coinPairChoiceService.updateByBindId(originalBindId,newBindId));
    }

}
