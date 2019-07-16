package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.TradePlatformEnum;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.TradePlatform;
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
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/15 18:01
 */
@RestController
@RequestMapping("/apis")
@Validated
@Api(value = "交易平台接口")
public class TradePlatformController {

    @Resource
    TradePlatformService tradePlatformService;

    @Resource
    private DiscoveryClient client ;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @ApiOperation(value = "获取交易平台分页信息")
    @GetMapping("/")
    public PageInfo list(){

        return this.tradePlatformService.listByPage(0,pageSizeCommon);
    }

    @ApiOperation(value = "获取交易平台单个信息接口")
    @GetMapping("/{id}")
    public TradePlatform get(@PathVariable("id") @Min(1) int id){
        return this.tradePlatformService.get(id).orElseThrow(()-> new NotFoundException(TradePlatformEnum.NAME));
    }

    @ApiOperation(value = "添加交易平台单个信息接口")
    @PostMapping("/")
    public boolean add(@RequestBody @NotNull TradePlatform tradePlatform){
        return this.tradePlatformService.add(tradePlatform);
    }

    @ApiOperation(value = "更新交易平台接口")
    @PutMapping("/")
    public boolean update(@RequestBody @NotNull TradePlatform tradePlatform){
        return this.tradePlatformService.update(tradePlatform);
    }

    @ApiOperation(value = "删除交易平台接口")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable("id") @Min(1) int id){
        return this.tradePlatformService.delete(id);
    }

    @ApiOperation(value = "发现服务")
    @RequestMapping("/discover")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }
}
