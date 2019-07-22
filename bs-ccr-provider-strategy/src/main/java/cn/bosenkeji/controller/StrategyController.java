package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/strategy")
@Validated
@Api(tags = "strategy 策略相关接口" , value = "提供策略相关的接口 Rest API")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    @Resource
    private DiscoveryClient client;

    @RequestMapping(value = "/" , method = RequestMethod.POST)
    @ApiOperation(value = "添加策略信息",notes = "对策略基本属性的添加",
            nickname = "addStrategyBySelective",httpMethod = "POST"
    )
    public boolean addStrategyBySelective(@RequestBody @NotNull Strategy strategy) {
        return strategyService.addStrategyAttributeBySelective(strategy);
    }

    @RequestMapping(value = "/attribute/",method = RequestMethod.POST)
    @ApiOperation(value = "添加策略的详细信息",notes = "对策略的详细属性的添加",
            nickname = "addStrategyAttribute",httpMethod = "POST"
    )
    public boolean addStrategyAttribute(@RequestBody @NotNull StrategyAttribute strategyAttribute) {
        return strategyService.insertStrategyAttributeBySelective(strategyAttribute);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定策略" , notes = "通过策略Id获取策略的详细信息",
            nickname = "getStrategyById",httpMethod = "GET"
    )
    public StrategyVO get(@PathVariable("id") @Min(value = 1) Integer id) {
        return strategyService.getStrategy(id);
    }

    @RequestMapping(value="/" , method = RequestMethod.GET)
    @ApiOperation(value = "获取策略列表", notes = "带分页，默认从第一页开始，每页10条记录",
            nickname = "findStrategyByPage",httpMethod = "GET"
    )
    public PageInfo listByPage(
            @RequestParam("pageNum") @Min(value = 1) Integer pageNum,
            @RequestParam("pageSize") @Min(value = 1) Integer pageSize
            ){
        return strategyService.listByPage(pageNum,pageSize);
    }

    @RequestMapping(value = "/discover" , method = RequestMethod.GET)
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口",
            nickname = "discover",httpMethod = "GET"
    )
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
