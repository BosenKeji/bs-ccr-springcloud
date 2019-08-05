package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategySequenceServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategySequence;
import cn.bosenkeji.vo.strategy.StrategySequenceOther;
import cn.bosenkeji.vo.strategy.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(value = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class
        ,fallbackFactory = IStrategySequenceServiceFallbackFactory.class
)
public interface IStrategySequenceService {

    @PostMapping("/strategy_sequence/")
    Result insertStrategySequenceBySelective(@RequestBody StrategySequence sequence);

    @PostMapping("/strategy_sequence/value/")
    Result insertStrategySequenceValueBySelective(@RequestBody StrategySequenceValue sequenceValue);

    @GetMapping("/strategy_sequence/")
    PageInfo<StrategySequence> findAll(
            @RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @GetMapping("/strategy_sequence/{id}")
    StrategySequenceOther findSequenceByPrimaryKey(@PathVariable("id") Integer id);

    @GetMapping("/strategy_sequence/value/{strategyId}")
    String getSequenceValueByStrategyId(@PathVariable("strategyId") Integer strategyId);
}
