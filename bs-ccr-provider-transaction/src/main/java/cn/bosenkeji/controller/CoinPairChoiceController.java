package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicEnum;
import cn.bosenkeji.service.CoinPairChoicService;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author CAJR
 * @create 2019/7/17 16:06
 */
@RestController
@RequestMapping("/coin_pair_choice")
@Validated
@Api(tags = "CoinPairChoice 自选货币接口",value = "自选货币相关功能 rest接口")
public class CoinPairChoiceController {

    @Resource
    CoinPairChoicService coinPairChoicService;
    @Resource
    DiscoveryClient client;


    @ApiOperation(value = "获取自选货币分页接口",httpMethod = "GET",nickname = "getListCoinPairChoiceWithPage")
    @GetMapping("/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon){
        return this.coinPairChoicService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个自选货币接口",httpMethod = "GET",nickname = "getOneCoinPairChoice")
    @GetMapping("/{id}")
    public CoinPairChoice get(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairChoicService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicEnum.NAME));
    }

    @ApiOperation(value = "添加自选货币接口",httpMethod = "POST",nickname = "addOneCoinPairChoice")
    @PostMapping("/")
    public boolean add(@RequestParam("userId")  @ApiParam(value = "用户id", required = true, type = "integer",example = "1") int userId,
                       @RequestParam("strategyStatus")  @ApiParam(value = "策略状态", required = true, type = "integer",example = "1") int strategyStatus,
                       @RequestParam("coinPairId")  @ApiParam(value = "货币对id", required = true, type = "integer",example = "1") int coinPairId){
        CoinPairChoice coinPairChoice =new CoinPairChoice();
        coinPairChoice.setUserId(userId);
        coinPairChoice.setCoinPartnerId(coinPairId);
        coinPairChoice.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        if (strategyStatus == 1){
            coinPairChoice.setIsStart(1);
        }

        return this.coinPairChoicService.add(coinPairChoice);
    }

    @ApiOperation(value = "更新自选货币接口",httpMethod = "PUT",nickname = "updateOneCoinPairChoice")
    @PutMapping("/")
    public boolean update(@RequestBody @ApiParam(value = "自选币实体", required = true, type = "string") CoinPairChoice coinPairChoice){
        coinPairChoice.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.coinPairChoicService.update(coinPairChoice);
    }

    @ApiOperation(value = "删除自选货币接口",httpMethod = "DELETE",nickname = "deleteOneCoinPairChoice")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) @ApiParam(value = "自选币ID", required = true, type = "integer",example = "1") int id){
        return this.coinPairChoicService.delete(id);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
