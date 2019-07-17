package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.CoinPairChoicEnum;
import cn.bosenkeji.service.CoinPairChoicService;
import cn.bosenkeji.vo.CoinPair;
import cn.bosenkeji.vo.CoinPairChoic;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author CAJR
 * @create 2019/7/17 16:06
 */
@RestController
@RequestMapping("/coinpairchoic")
@Validated
@Api(value = "自选货币接口")
public class CoinPairChoicController {
    @Resource
    CoinPairChoicService coinPairChoicService;
    @Resource
    DiscoveryClient client;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取自选货币分页接口")
    @GetMapping("/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum){
        return this.coinPairChoicService.listByPage(pageNum,pageSizeCommon);
    }

    @ApiOperation(value = "获取单个自选货币接口")
    @GetMapping("/{id}")
    public CoinPairChoic get(@PathVariable("id") @Min(1) int id){
        return this.coinPairChoicService.get(id).orElseThrow(()->new NotFoundException(CoinPairChoicEnum.NAME));
    }

    @ApiOperation(value = "添加自选货币接口")
    @PostMapping("/")
    public boolean add(@RequestBody @NotNull User user, @RequestBody @NotNull Strategy strategy, @RequestBody @NotNull CoinPair coinPair){
        CoinPairChoic coinPairChoic=new CoinPairChoic();
        coinPairChoic.setUserId(user.getId());
        coinPairChoic.setCoinPartnerId(coinPair.getId());

        if (strategy.getStatus() == 1){
            coinPairChoic.setIsStart(1);
        }

        return this.coinPairChoicService.add(coinPairChoic);
    }

    @ApiOperation(value = "更新自选货币接口")
    @PutMapping("/")
    public boolean update(@RequestBody CoinPairChoic coinPairChoic){
        return this.coinPairChoicService.update(coinPairChoic);
    }

    @ApiOperation(value = "删除自选货币接口")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) int id){
        return this.coinPairChoicService.delete(id);
    }


    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
