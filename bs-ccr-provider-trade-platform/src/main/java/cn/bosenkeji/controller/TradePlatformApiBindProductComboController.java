package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.TradePlatformApiBindProductComboEnum;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-26 15:48
 */
@RestController
@RequestMapping("/trade_platform_api_bind_product_combo")
@Validated
@Api(tags = "tradePlatformApiBindProductCombo 交易平台api绑定用户套餐接口",value = "提供交易平台api绑定用户套餐相关功能 Rest接口")
public class TradePlatformApiBindProductComboController {

    @Autowired
    private TradePlatformApiBindProductComboService tradePlatformApiBindProductComboService;

    @GetMapping("/list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取交易平台api绑定用户套餐列表 api接口"
            ,httpMethod = "GET",nickname = "getTradePlatformApiBindProductComboListByUserIdWithPage")
    public PageInfo getListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                    @RequestParam("userId") @Min(1) @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return tradePlatformApiBindProductComboService.findByUserIdWithPage(userId,pageNum,pageSize);
    }

    @PostMapping("/")
    @ApiOperation(value = "添加 交易平台api绑定用户套餐 api接口"
            ,httpMethod = "POST",nickname = "addTradePlatformApiBindProductComboList")
    public Result add(@RequestBody @NotNull @ApiParam(value = "交易谱平台api绑定用户套餐实体",required = true,type = "string") TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        //判断该交易平台api是否未用户未绑定的
       /* tradePlatformApiBindProductComboService.checkExistByUserIdAndTradePlatformApiId(
                tradePlatformApiBindProductCombo.getUserId()
                , tradePlatformApiBindProductCombo.getTradePlatformApiId())
                .filter((value)->value==1)
                .orElseThrow(()->new AddException(TradePlatformApiBindProductComboEnum.NAME));*/

        //判断该套餐是否为用户为绑定的
        /*tradePlatformApiBindProductComboService.checkExistByUserIdAndUserProductComboId(
                tradePlatformApiBindProductCombo.getUserId()
                ,tradePlatformApiBindProductCombo.getUserProductComboId())
                .filter((value)->value==1)
                .orElseThrow(()->new AddException(TradePlatformApiBindProductComboEnum.NAME));*/

        tradePlatformApiBindProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApiBindProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(tradePlatformApiBindProductComboService.add(tradePlatformApiBindProductCombo)
                .filter((value)->value>=1)
                .orElseThrow(()->new AddException(TradePlatformApiBindProductComboEnum.NAME)));
    }

    @PutMapping("/{id}")
    @ApiOperation(value="更新 交易品台绑定用户套餐",httpMethod = "PUT",nickname = "updateTradePlatformApiBindProductCombo")
    public Result update(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                         @RequestParam("tradePlatformApiId") @ApiParam(value = "交易平台api ID",required = true,type = "integer",example = "1") int tradePlatformApiId,
                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {
        //判断该 api绑定是否为 该用户下的 否则不能操作
        tradePlatformApiBindProductComboService.checkExistByUserIdAndId(userId,id).filter((value)->value==1)
                .orElseThrow(()->new UpdateException(TradePlatformApiBindProductComboEnum.NAME));

        //判断该交易平台api是否未用户未绑定的
        /*tradePlatformApiBindProductComboService.checkExistByUserIdAndTradePlatformApiId(
                userId
                , tradePlatformApiId)
                .filter((value)->value==1)
                .orElseThrow(()->new UpdateException(TradePlatformApiBindProductComboEnum.NAME));*/

        TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo=new TradePlatformApiBindProductCombo();
        tradePlatformApiBindProductCombo.setId(id);
        tradePlatformApiBindProductCombo.setTradePlatformApiId(tradePlatformApiId);
        tradePlatformApiBindProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(this.tradePlatformApiBindProductComboService.updateBindApi(tradePlatformApiBindProductCombo));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="解除 交易品台绑定用户套餐",httpMethod = "DELETE",nickname = "deleteTradePlatformApiBindProductCombo")
    public Result delete(@PathVariable("id") @ApiParam(value = "交易平台绑定用户套餐ID",required = true,type = "integer",example = "1") int id,
                         @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        //判断该 api绑定是否为 该用户下的 否则不能操作
        tradePlatformApiBindProductComboService.checkExistByUserIdAndId(userId,id).filter((value)->value==1)
                .orElseThrow(()->new DeleteException(TradePlatformApiBindProductComboEnum.NAME));

        return new Result<>(this.tradePlatformApiBindProductComboService.delete(id));
    }

    @GetMapping("/get_no_bind_trade_platform_api_list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取用户未绑定的交易平台api列表 api接口"
            ,httpMethod = "GET",nickname = "getNoBindTradePlatformApiListByUserIdWithPage")
    public PageInfo getNoBindTradePlatformApiListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                    @RequestParam("userId") @Min(1) @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return tradePlatformApiBindProductComboService.findNoBindTradePlatformApiListByUserId(userId,pageNum,pageSize);
    }

    @GetMapping("/get_no_bind_user_product_combo_list_by_user_id")
    @ApiOperation(value = "根据用户ID 获取用户未绑定的用户套餐列表 api接口"
            ,httpMethod = "GET",nickname = "getNoBindUserProductComboListByUserIdWithPage")
    public PageInfo getNoBindUserProductComboListByUserId(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                                    @RequestParam("userId") @Min(1) @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return tradePlatformApiBindProductComboService.findNoBindUserProductComboListByUserId(userId,pageNum,pageSize);
    }

}
