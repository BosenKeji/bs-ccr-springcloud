package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/strategy")
@Validated
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @Resource
    private DiscoveryClient client;

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定策略" , notes = "通过策略Id获取策略的详细信息")
    public StrategyVO get(@PathVariable("id") @Min(value = 1) Integer id) {
        return strategyService.getStrategy(id);
    }

    @RequestMapping(value="/" , method = RequestMethod.GET)
    @ApiOperation(value = "获取策略列表", notes = "带分页，默认从第一页开始，每页10条记录")
    public PageInfo listByPage(){
        return strategyService.listByPage(0,pageSizeCommon);
    }

    @RequestMapping(value = "/discover" , method = RequestMethod.GET)
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
