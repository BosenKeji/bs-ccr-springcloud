package cn.bosenkeji.controller;

import cn.bosenkeji.interfaces.RedisInterface;
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
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/strategy_sequence")
@Validated
@Api(tags = "strategySequence 策略数列相关接口", value = "提供策略数列的相关接口 Rest API")
@CacheConfig(cacheNames = "ccr:strategySequence")
public class StrategySequenceController {

    @Autowired
    private StrategySequenceService strategySequenceService;


    @Resource
    private DiscoveryClient client;


    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.STRATEGY_SEQUENCE_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.STRATEGY_LIST_KEY,allEntries = true)
            }
    )
    @PostMapping(value = "/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的基本信息进行添加",nickname = "insertStrategySequence",httpMethod = "POST")
    public Result insertStrategySequence(
            @RequestBody @NotNull @ApiParam(value = "数列基本属性映射的对象",required = true) StrategySequence sequence
    ) {
        boolean checkSequenceId = strategySequenceService.checkSequenceById(sequence.getId()).get() > 0;
        boolean checkSequenceName = strategySequenceService.checkSequenceByName(sequence.getName()).get() > 0;
        if (checkSequenceId || checkSequenceName) {
            return new Result<>(0,"添加失败，该数列已存在！");
        }
        return new Result<>(strategySequenceService.insertStrategySequenceBySelective(sequence));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.STRATEGY_SEQUENCE_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.STRATEGY_LIST_KEY,allEntries = true)
            }
    )
    @PostMapping(value = "/value/")
    @ApiOperation(value = "添加策略数列信息", notes = " 对数列的值信息进行添加",nickname = "insertStrategySequenceValue",httpMethod = "POST")
    public Result insertStrategySequenceValue(
            @RequestBody @NotNull @ApiParam(value = "数列详细信息映射的对象",required = true) StrategySequenceValue sequenceValue
    ) {
        boolean checkSequenceId = strategySequenceService.checkSequenceById(sequenceValue.getStrategySequenceId()).get() > 0;
        if (!checkSequenceId) {
            return new Result<>(0,"添加数列值失败，对应的数列信息不存在！");
        }
        boolean checkIdAndValueAndSequenceId = strategySequenceService.checkSequenceByIdOrValueOrSequenceId(
                sequenceValue.getId(),sequenceValue.getValue(),sequenceValue.getStrategySequenceId()).get() > 0;
        if (checkIdAndValueAndSequenceId) {
            return new Result<>(0,"添加失败，该数列信息已存在！");
        }
        return new Result<>(strategySequenceService.insertStrategySequenceValueBySelective(sequenceValue));
    }

    @Cacheable(value = RedisInterface.STRATEGY_SEQUENCE_LIST_KEY,key = "#pageNum+'-'+#pageSize")
    @GetMapping(value = "/")
    @ApiOperation(value = "获取数列列表",notes = "带分页")
    public PageInfo<StrategySequence> findAll(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(value = 1) @ApiParam(value = "分页起始页",example = "1",required = true)  Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(value = 1) @ApiParam(value = "每页条数",example = "3",required = true) Integer pageSize
    ) {
        PageInfo pageInfo = strategySequenceService.findAll(pageNum, pageSize);
        return pageInfo;
    }

    @Cacheable(value = RedisInterface.STRATEGY_SEQUENCE_ID_KEY,key = "#id")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取指定数列信息",notes = "通过数列Id获取指定数列的信息")
    public Result findSequenceByPrimaryKey(
            @PathVariable("id") @Min(value = 0) @ApiParam(value = "数列ID",required = true,example = "1") Integer id
    ){
        boolean checkSequenceId = strategySequenceService.checkSequenceById(id).get() > 0;
        if (!checkSequenceId) {
            StrategySequenceOther sequenceOther = new StrategySequenceOther();
            return new Result<>(sequenceOther,"数列不存在");
        }
        return strategySequenceService.findSequenceByPrimaryKey(id);
    }


    @GetMapping(value = "/discover")
    @ApiOperation(value = "获取当前服务的API接口" , notes = "获取当前服务API接口")
    @ApiIgnore
    public Object discover() { // 直接返回发现服务信息
        return this.client ;
    }

}
