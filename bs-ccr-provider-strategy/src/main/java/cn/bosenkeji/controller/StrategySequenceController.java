package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/strategysequence")
@Validated
@Api
public class StrategySequenceController {

    @Autowired
    private StrategySequenceService strategySequenceService;


    @Resource
    private DiscoveryClient client;



    @RequestMapping(value = "/",method = RequestMethod.POST)
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的基本信息进行添加",nickname = "insertStrategySequence",httpMethod = "POST")
    public boolean insertStrategySequence(@RequestBody StrategySequence sequence) {
        return strategySequenceService.insertStrategySequenceBySelective(sequence);
    }

    @RequestMapping(value = "/value/",method = RequestMethod.POST)
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的值信息进行添加",nickname = "insertStrategySequenceValue",httpMethod = "POST")
    public boolean insertStrategySequenceValue(@RequestBody StrategySequenceValue sequenceValue) {
        return strategySequenceService.insertStrategySequenceValueBySelective(sequenceValue);
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "获取数列列表",notes = "带分页")
    public PageInfo<StrategySequence> findAll(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize
    ) {
        return strategySequenceService.findAll(pageNum, pageSize);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") @Min(value = 0) Integer id){
        return strategySequenceService.findSequenceByPrimaryKey(id);
    }

    @RequestMapping(value = "/value/{strategyId}" , method = RequestMethod.GET)
    @ApiOperation(value = "获取指定数列信息",notes = "通过策略Id获取对应的数列的值")
    public String getSequenceValueByStrategyId(@PathVariable("strategyId") @Min(value = 1) Integer strategyId) {
        return strategySequenceService.getSequenceValueByStrategyId(strategyId);
    }

    @RequestMapping(value = "/discover" , method = RequestMethod.GET)
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
