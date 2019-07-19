package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategySequenceServiceFallbackFactory;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import cn.bosenkeji.vo.StrategySequenceValue;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "bs-ccr-provider-strategy",configuration = FeignClientConfig.class,fallbackFactory = IStrategySequenceServiceFallbackFactory.class)
public interface IStrategySequenceService {

    @PostMapping("/strategysequence/")
    boolean insertStrategySequenceBySelective(@RequestBody StrategySequence sequence);

    @PostMapping("/strategysequence/value/")
    boolean insertStrategySequenceValueBySelective(@RequestBody StrategySequenceValue sequenceValue);

    @GetMapping("/strategysequence/")
    PageInfo<StrategySequence> findAll(
            @RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @GetMapping("/strategysequence/{id}")
    StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") Integer id);

    @GetMapping("/strategysequence/value/{strategyId}")
    String getSequenceValueByStrategyId(@PathVariable("strategyId") Integer strategyId);
}
