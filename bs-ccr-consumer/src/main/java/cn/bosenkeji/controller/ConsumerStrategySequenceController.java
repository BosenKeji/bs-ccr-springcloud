package cn.bosenkeji.controller;

import cn.bosenkeji.service.IStrategySequenceService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/strategy_sequence")
public class ConsumerStrategySequenceController {

    @Resource
    private IStrategySequenceService strategySequenceService;

    @GetMapping(value = "/")
    @ApiOperation(value = "获取数列列表",notes = "带分页")
    public PageInfo<StrategySequence> findAll(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(value = 1) @ApiParam(value = "分页起始页",example = "1",required = true)  Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(value = 1) @ApiParam(value = "每页条数",example = "3",required = true) Integer pageSize
    ) {
        return strategySequenceService.findAll(pageNum, pageSize);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public Result findSequenceByPrimaryKey(
            @PathVariable("id") @Min(value = 0) @ApiParam(value = "数列ID",required = true,example = "1") Integer id
    ){
        return strategySequenceService.findSequenceByPrimaryKey(id);
    }


    @PostMapping(value = "/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的基本信息进行添加",nickname = "insertStrategySequence",httpMethod = "POST")
    public Result insertStrategySequence(
            @NotNull @ApiParam(value = "数列基本属性映射的对象",required = true) StrategySequence sequence
    ) {
        return strategySequenceService.insertStrategySequenceBySelective(sequence);
    }

    @PostMapping(value = "/value/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的值信息进行添加",nickname = "insertStrategySequenceValue",httpMethod = "POST")
    public Result insertStrategySequenceValue(
            @NotNull @ApiParam(value = "数列详细信息映射的对象",required = true) StrategySequenceValue sequenceValue
    ) {
        return strategySequenceService.insertStrategySequenceValueBySelective(sequenceValue);
    }
}
