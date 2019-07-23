package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Optional<Integer> insertStrategySequence(@RequestBody @NotNull StrategySequence sequence) {
        return strategySequenceService.insertStrategySequenceBySelective(sequence);
    }

    @PostMapping(value = "/value/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的值信息进行添加",nickname = "insertStrategySequenceValue",httpMethod = "POST")
    public Optional<Integer> insertStrategySequenceValue(@RequestBody @NotNull StrategySequenceValue sequenceValue) {
        return strategySequenceService.insertStrategySequenceValueBySelective(sequenceValue);
    }

    @GetMapping(value = "/")
    @ApiOperation(value = "获取数列列表",notes = "带分页")
    public PageInfo<StrategySequence> findAll(
            @RequestParam("pageNum") @Min(value = 1) Integer pageNum,
            @RequestParam("pageSize") @Min(value = 1) Integer pageSize
    ) {
        return strategySequenceService.findAll(pageNum, pageSize);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") @Min(value = 0) Integer id){
        return strategySequenceService.findSequenceByPrimaryKey(id);
    }

    @GetMapping(value = "/value/{strategyId}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过策略Id获取对应的数列的值")
    public String getSequenceValueByStrategyId(@PathVariable("strategyId") @Min(value = 1) Integer strategyId) {
        return strategySequenceService.getSequenceValueByStrategyId(strategyId);
    }

    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
