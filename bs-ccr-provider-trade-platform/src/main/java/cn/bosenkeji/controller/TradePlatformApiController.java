package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.cache.BatchCacheRemove;
import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.TradePlatformApiEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.util.RsaUtils;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
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
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * add cache by xivin
 * 与 tradePlatform 交易平台关联查询
 */

/**
 * @Author CAJR
 * @create 2019/7/15 15:06
 */
@RestController
@RequestMapping("/trade_platform_apis")
@Validated
@Api(tags = "tradePlatformApi 交易平台api接口",value = "提供交易平台api相关功能 Rest接口")
@CacheConfig(cacheNames = "ccr:tradePlatformApi")
public class TradePlatformApiController {
    @Resource
    TradePlatformApiService tradePlatformApiService;

    @Resource
    private DiscoveryClient client ;

    @ApiOperation(value = "获取交易平台api列表接口",notes = "交易平台api列表",httpMethod = "GET",nickname = "getListTradePlatformApiByPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon,
                         @RequestParam("userId") @ApiParam(value = "用户ID", required = true, type = "integer",example = "1") int userId){
        return this.tradePlatformApiService.listByPage(pageNum,pageSizeCommon,userId);
    }


    @ApiOperation(value = "根据tradePlatformApiId获取交易平台api单个信息接口",notes = "交易平台api单个信息接口",httpMethod = "GET",nickname = "getOneTradePlatformApi")
    @GetMapping("/{id}")
    public TradePlatformApi get(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台api id", required = true, type = "integer",example = "1") int id){
        return this.tradePlatformApiService.get(id);
    }

    @ApiOperation(value = "添加交易平台api单个信息接口",notes = "添加交易平台api单个信息接口",httpMethod = "POST",nickname = "addOneTradePlatformApi")
    @PostMapping("/")
    public Result add(@RequestBody  @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        if (this.tradePlatformApiService.checkExistByUserIdAndNickName(tradePlatformApi.getUserId(),tradePlatformApi.getNickname()).get() >= 1){
            return new Result<>(null,"该用户的nickName已存在");
        }
        if (this.tradePlatformApiService.checkExistBySignAndStatus(tradePlatformApi.getUserId(),tradePlatformApi.getSign(),1).get() > 0){
            return new Result<>(null,"该用户的sign已存在，添加失败！");
        }
        tradePlatformApi.setStatus(1);
        tradePlatformApi.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApi.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Optional<Integer> result = this.tradePlatformApiService.add(tradePlatformApi);

        if (result.get() == -2){
            return new Result<>(null,"密文校验失败！");
        }
        if (result.get() == -1){
            return new Result<>(null,"key已存在");
        }


        return new Result<>(result);
    }


    @ApiOperation(value = "更新交易平台api接口",notes = "更新交易平台api接口",httpMethod = "PUT",nickname = "updateOneTradePlatformApi")
    @PutMapping("/")
    public Result update(@RequestBody @NotNull @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        TradePlatformApi existTradePlatformApi = this.tradePlatformApiService.get(tradePlatformApi.getId());

        if (existTradePlatformApi == null){
            return new Result<>(null,"交易平台API不存在");
        }

        if (this.tradePlatformApiService.checkExistByUserIdAndNickName(tradePlatformApi.getUserId(),tradePlatformApi.getNickname()).get() > 0
                && !existTradePlatformApi.getNickname().equals(tradePlatformApi.getNickname())){
            return new Result<>(null,"该用户的nickName已存在");
        }
        if (tradePlatformApi.getSign() != null){
            if (this.tradePlatformApiService.checkExistBySignAndStatus(tradePlatformApi.getUserId(),tradePlatformApi.getSign(),1).get() >= 1
            && !existTradePlatformApi.getSign().equals(tradePlatformApi.getSign())){
                return new Result<>(null,"该API的账号已经绑定了机器人，在取消绑定之前，请勿重复绑定！");
            }
        }

        tradePlatformApi.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        Optional<Integer> result = this.tradePlatformApiService.update(tradePlatformApi);
        if (result.get() == -1){
            return new Result<>(null,"key已存在");
        }
        return new Result<>(result);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,key = "#id",condition = "#result.data != null")
            }
    )
    @BatchCacheRemove(value = "'"+RedisInterface.TRADE_PLATFORM_API_LIST_KEY+"::'+#userId+'-*'",condition = "#result.data != null")
    @ApiOperation(value = "删除交易平台api接口",notes = "删除平台api接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformApi")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台api id", required = true, type = "integer",example = "1") int id,
                         @RequestParam("userId")@ApiParam(value = "用户 id", required = true, type = "integer",example = "1") @Min(1) int userId){
        TradePlatformApi tradePlatformApi = this.tradePlatformApiService.get(id);

        if (tradePlatformApi == null){
            return new Result<>(null,"交易平台API不存在");
        }
        if (tradePlatformApi.getUserId() != userId){
            return new Result<>(null,"非法操作，不能删除其他用户的东西哦");
        }

        return new Result<>(this.tradePlatformApiService.delete(id));
    }

    /**
     * 与 tradePlatform 关联查询
     * @param userId
     * @return
     */
    @GetMapping("/user/{userId}")
    public TradePlatformApi selectByUserId(@PathVariable("userId") int userId) {
        return tradePlatformApiService.getByUserId(userId);
    }

    @GetMapping("/all")
    @ApiIgnore
    public List<TradePlatformApi> findAll() {
        return tradePlatformApiService.findAll();
    }

    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

    @GetMapping("/oss_pri_key_cover")
    public Result priKeyCoverOss(){
        RsaUtils.downloadPrivateKeyByOSS();
        return new Result<>(1);
    }

}
