package cn.bosenkeji.controller;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.Coin;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName ConsumerCoinController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@RestController
@RequestMapping("/coin")
@Api(tags = "Coin 货币相关接口", value = "提供货币相关接口的 Rest API")
public class ConsumerCoinController {
    @Resource
    private ICoinClientService iCoinClientService;

    @ApiOperation(value = "获取单个货币信息接口", httpMethod = "GET", nickname = "getCoinListWithPage")
    @GetMapping("/{id}")
    public Coin get(@PathVariable("id")  @ApiParam(value = "币种ID", required = true, type = "integer",example = "1") int id) {
        return iCoinClientService.get(id);
    }

    @ApiOperation(value = "获取货币列表接口", httpMethod = "GET",nickname = "getOneCoin")
    @GetMapping("/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                             @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) {
        return iCoinClientService.list(pageNum, pageSizeCommon);
    }

    @ApiOperation(value = "添加单个货币信息接口", httpMethod = "POST",nickname = "addCoin")
    @PostMapping("/")
    public Result add(@RequestBody @ApiParam(value = "币种实体", required = true, type = "string") Coin coin) {

        return iCoinClientService.add(coin);
    }

    @ApiOperation(value = "更新单个货币信息接口", httpMethod = "PUT" ,nickname = "updateCoin")
    @PutMapping("/")
    public Result update(@RequestBody @ApiParam(value = "币种实体", required = true, type = "string") Coin coin){
        return iCoinClientService.update(coin);
    }

    @ApiOperation(value = "删除单个货币信息接口", httpMethod = "DELETE",nickname = "deleteOneCoin")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "币种ID", required = true, type = "integer",example = "1") int id){
        return iCoinClientService.delete(id);
    }

}
