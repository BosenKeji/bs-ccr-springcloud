package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.enums.StrategySequenceEnum;
import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceOther;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
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
import javax.validation.constraints.NotNull;
import java.util.Optional;


@RestController
@RequestMapping("/strategy_sequence")
@Validated
@Api(tags = "策略数列相关接口", value = "提供策略数列的相关接口 Rest API")
public class StrategySequenceController {

    @Autowired
    private StrategySequenceService strategySequenceService;


    @Resource
    private DiscoveryClient client;



    @PostMapping(value = "/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的基本信息进行添加",nickname = "insertStrategySequence",httpMethod = "POST")
    public Result insertStrategySequence(
            @RequestBody @NotNull @ApiParam(value = "数列基本属性映射的对象",required = true) StrategySequence sequence
    ) {
        strategySequenceService.checkSequenceByName(sequence.getName())
                .filter((value)->value==0)
                .orElseThrow( ()-> new AddException(StrategySequenceEnum.STRATEGY_SEQUENCE_EXIST));
        return new Result(strategySequenceService.insertStrategySequenceBySelective(sequence));
    }

    @PostMapping(value = "/value/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的值信息进行添加",nickname = "insertStrategySequenceValue",httpMethod = "POST")
    public Result insertStrategySequenceValue(
            @RequestBody @NotNull @ApiParam(value = "数列详细信息映射的对象",required = true) StrategySequenceValue sequenceValue
    ) {
        strategySequenceService.checkSequenceById(sequenceValue.getStrategySequenceId())
                .filter((v)->v!=0)
                .orElseThrow(()-> new AddException(StrategySequenceEnum.STRATEGY_SEQUENCE_NOT_FOUND));
        return new Result(strategySequenceService.insertStrategySequenceValueBySelective(sequenceValue));
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "获取数列列表",notes = "带分页")
    public PageInfo<StrategySequence> findAll(
            @RequestParam("pageNum") @Min(value = 1) @ApiParam(value = "分页起始页",example = "1",required = true)  Integer pageNum,
            @RequestParam("pageSize") @Min(value = 1) @ApiParam(value = "每页条数",example = "3",required = true) Integer pageSize
    ) {
        PageInfo pageInfo = strategySequenceService.findAll(pageNum, pageSize);
        return pageInfo;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public Result findSequenceByPrimaryKey(
            @PathVariable("id") @Min(value = 0) @ApiParam(value = "数列ID",required = true,example = "1") Integer id
    ){
        return new Result(strategySequenceService.findSequenceByPrimaryKey(id));
    }

    @GetMapping(value = "/value/{strategyId}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过策略Id获取对应的数列的值")
    public Result getSequenceValueByStrategyId(
            @PathVariable("strategyId") @Min(value = 1) @ApiParam(value = "数列ID",required = true,example = "1") Integer strategyId
    ) {
        return new Result(strategySequenceService.getSequenceValueByStrategyId(strategyId));
    }

    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
