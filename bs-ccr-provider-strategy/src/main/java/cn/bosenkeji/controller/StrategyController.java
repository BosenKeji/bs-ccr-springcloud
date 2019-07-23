package cn.bosenkeji.controller;

import cn.bosenkeji.service.StrategyService;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
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
import java.util.Optional;


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
    public Optional<Integer> addStrategyBySelective(
            @RequestBody Strategy strategy
    ) {
        return strategyService.addStrategyAttributeBySelective(strategy);
    }

    @PostMapping(value = "/attribute/")
    @ApiOperation(value = "添加策略的详细信息",notes = "对策略的详细属性的添加",
            nickname = "addStrategyAttributeBySelective",httpMethod = "POST"
    )
    public Optional<Integer> addStrategyAttributeBySelective(
            @RequestBody StrategyAttribute strategyAttribute
    ) {
        return strategyService.insertStrategyAttributeBySelective(strategyAttribute);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定策略" , notes = "通过策略Id获取策略的详细信息",
            nickname = "getStrategyById",httpMethod = "GET"
    )
    public StrategyVO get(
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

    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口",
            nickname = "discover",httpMethod = "GET"
    )
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
