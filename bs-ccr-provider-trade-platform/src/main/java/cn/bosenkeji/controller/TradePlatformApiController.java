package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.TradePlatformApiEnum;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

/**
 * @Author CAJR
 * @create 2019/7/15 15:06
 */
@RestController
@RequestMapping("/trade_platform_apis")
@Validated
@Api(tags = "tradePlatformApi 交易平台api接口",value = "提供交易平台api相关功能 Rest接口")
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
        if (this.tradePlatformApiService.checkExistByKeyAndStatus(tradePlatformApi.getUserId(),tradePlatformApi.getAccessKey(),tradePlatformApi.getSecretKey(),1).get() >= 1){
            return new Result<>(null,"key已存在");
        }

        tradePlatformApi.setStatus(1);
        tradePlatformApi.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApi.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.tradePlatformApiService.add(tradePlatformApi));
    }

    @ApiOperation(value = "更新交易平台api接口",notes = "更新交易平台api接口",httpMethod = "PUT",nickname = "updateOneTradePlatformApi")
    @PutMapping("/")
    public Result update(@RequestBody @NotNull @ApiParam(value = "交易平台API实体", required = true, type = "string") TradePlatformApi tradePlatformApi){
        if (this.tradePlatformApiService.get(tradePlatformApi.getId()) == null){
            return new Result<>(null,"交易平台API不存在");
        }

        if (this.tradePlatformApiService.checkExistByUserIdAndNickName(tradePlatformApi.getUserId(),tradePlatformApi.getNickname()).get() > 0){
            return new Result<>(null,"该用户的nickName已存在");
        }

        tradePlatformApi.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.tradePlatformApiService.update(tradePlatformApi));
    }

    @ApiOperation(value = "删除交易平台api接口",notes = "删除平台api接口",httpMethod = "DELETE",nickname = "deleteOneTradePlatformApi")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "交易平台api id", required = true, type = "integer",example = "1") int id){

        if (this.tradePlatformApiService.get(id) == null){
            return new Result<>(null,"交易平台API不存在");
        }

        return new Result<>(this.tradePlatformApiService.delete(id));
    }

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

//    @GetMapping("/public_key")
//    public String getPublicKey(){
//
//    }
}
