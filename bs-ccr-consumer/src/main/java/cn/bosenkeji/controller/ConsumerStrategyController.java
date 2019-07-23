package cn.bosenkeji.controller;

import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Optional;


@RequestMapping("/strategy")
@RestController
@Validated
public class ConsumerStrategyController {

    @Resource
    private IStrategyService strategyService;

    @PostMapping(value = "/")
    @ApiOperation(value = "添加策略信息",notes = "对策略基本属性的添加",
            nickname = "addStrategyBySelective",httpMethod = "POST"
    )
    public Optional<Integer> addStrategyBySelective(
            @ApiParam("策略基本属性映射的对象") @NotNull Strategy strategy
    ) {
        return strategyService.addStrategyBySelective(strategy);
    }

    @PostMapping(value = "/attribute/")
    @ApiOperation(value = "添加策略的详细信息",notes = "对策略的详细属性的添加",
            nickname = "addStrategyAttributeBySelective",httpMethod = "POST"
    )
    public Optional<Integer> addStrategyAttributeBySelective(
            @ApiParam("策略的详细属性映射的对象") @NotNull StrategyAttribute strategyAttribute
    ) {
        return strategyService.addStrategyAttributeBySelective(strategyAttribute);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定策略" , notes = "通过策略Id获取策略的详细信息",
            nickname = "getStrategyById",httpMethod = "GET"
    )
    public StrategyOther get(
            @PathVariable("id") @Min(value = 1) @ApiParam(value = "策略的ID值",example = "1",required = true) Integer id
    ) {
        return strategyService.getStrategy(id);
    }

    @GetMapping(value="/")
    @ApiOperation(value = "获取策略列表", notes = "带分页，默认从第一页开始，每页10条记录",
            nickname = "findStrategyByPage",httpMethod = "GET"
    )
    public PageInfo listByPage(
            @RequestParam("pageNum") @Min(value = 1) @ApiParam(value = "分页的起始页",example = "1",required = true) Integer pageNum,
            @RequestParam("pageSize") @Min(value = 1) @ApiParam(value = "每页条数",example = "3",required = true) Integer pageSize
    ){
        return strategyService.listByPage(pageNum,pageSize);
    }



}
