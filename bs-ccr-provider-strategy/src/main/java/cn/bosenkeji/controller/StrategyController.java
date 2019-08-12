package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/strategy")
@Validated
@Api(tags = "strategy 策略相关接口" , value = "提供策略相关的接口 Rest API")
public class StrategyController {

    @Autowired
    private StrategyService strategyService;

    @Resource
    private DiscoveryClient client;

    @PostMapping(value = "/")
    @ApiOperation(value = "添加策略信息",notes = "对策略基本属性的添加",
            nickname = "addStrategyBySelective",httpMethod = "POST"
    )
    public Result addStrategyBySelective(
            @RequestBody Strategy strategy
    ) {
        boolean checkStrategyId = strategyService.checkStrategyById(strategy.getId()).get() > 0;
        boolean checkStrategyName = strategyService.checkStrategyByName(strategy.getName()).get() > 0;
        if (checkStrategyName || checkStrategyId) {
            return new Result<>(0 , "添加策略失败，该策略已存在！");
        }
        return new Result<>(strategyService.addStrategyBySelective(strategy));
    }

    @PostMapping(value = "/attribute/")
    @ApiOperation(value = "添加策略的详细信息",notes = "对策略的详细属性的添加",
            nickname = "addStrategyAttributeBySelective",httpMethod = "POST"
    )
    public Result addStrategyAttributeBySelective(
            @RequestBody StrategyAttribute strategyAttribute
    ) {
        boolean checkStrategyId = strategyService.checkStrategyById(strategyAttribute.getStrategyId()).get() > 0;
        if (!checkStrategyId) {
            return new Result<>(0,"策略属性添加失败，对应的策略信息不存在！");
        }
        boolean checkStrategyBySequenceId = strategyService.checkStrategyAttributeBySequenceId(strategyAttribute.getStrategySequenceId()).get() > 0;
        if (!checkStrategyBySequenceId) {
            return new Result<>(0,"添加失败，不存在对应的数列！");
        }
        boolean checkAttribute = strategyService.checkAttributeByIdOrNameOrStrategyId(
                strategyAttribute.getId(),strategyAttribute.getName(),strategyAttribute.getStrategyId()
        ).get() > 0;
        if (checkAttribute) {
            return new Result<>(0,"策略属性添加失败，该策略属性已存在！");
        }
        return new Result<>(strategyService.insertStrategyAttributeBySelective(strategyAttribute));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定策略" , notes = "通过策略Id获取策略的详细信息",
            nickname = "getStrategyById",httpMethod = "GET"
    )
    public StrategyOther get(
            @PathVariable("id") @Min(value = 1) @ApiParam(value = "策略的ID值",example = "1",required = true) Integer id
    ) {
        StrategyOther strategyOther = strategyService.getStrategy(id);
        if (strategyOther == null) {
            return new StrategyOther();
        }
        return strategyOther;
    }

    @GetMapping(value="/")
    @ApiOperation(value = "获取策略列表", notes = "带分页，默认从第一页开始，每页10条记录",
            nickname = "findStrategyByPage",httpMethod = "GET"
    )
    public PageInfo listByPage(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(value = 1) @ApiParam(value = "分页的起始页",example = "1",required = true) Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(value = 1) @ApiParam(value = "每页条数",example = "3",required = true) Integer pageSize
    ){
        return strategyService.listByPage(pageNum,pageSize);
    }

    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口",
            nickname = "discover",httpMethod = "GET"
    )
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
