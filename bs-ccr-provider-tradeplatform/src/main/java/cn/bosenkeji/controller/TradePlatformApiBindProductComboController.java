package cn.bosenkeji.controller;

import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
                                    @RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId) {

        return tradePlatformApiBindProductComboService.findByUserIdWithPage(pageNum,pageSize,userId);
    }

    @PostMapping("/")
    @ApiOperation(value = "添加 交易平台api绑定用户套餐 api接口"
            ,httpMethod = "POST",nickname = "addTradePlatformApiBindProductComboList")
    public Optional<Integer> add(@RequestBody  TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        return tradePlatformApiBindProductComboService.add(tradePlatformApiBindProductCombo);
    }
}
