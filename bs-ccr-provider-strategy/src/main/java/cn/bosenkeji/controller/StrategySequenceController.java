package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategySequenceService;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Min;


@RestController
@RequestMapping("/strategysequence")
@Validated
@Api
public class StrategySequenceController {

    @Autowired
    private StrategySequenceService strategySequenceService;

    @Value("${pageSize.common}")
    private int pageSizeCommon;

    @Resource
    private DiscoveryClient client;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ApiOperation(value = "获取数列列表",notes = "带分页，默认从第一页开始，每页10条")
    public PageInfo<StrategySequence> findAll() {
        return strategySequenceService.findAll(0, pageSizeCommon);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") @Min(value = 1) Integer id){
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
