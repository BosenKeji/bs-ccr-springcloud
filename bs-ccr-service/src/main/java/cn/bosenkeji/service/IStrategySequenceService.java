package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategySequenceServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IStrategyServiceFallbackFactory;
import cn.bosenkeji.vo.StrategySequence;
import cn.bosenkeji.vo.StrategySequenceVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bs-ccr-provider-strategy",configuration = FeignClientConfig.class,fallbackFactory = IStrategySequenceServiceFallbackFactory.class)
public interface IStrategySequenceService {

    @RequestMapping("/strategysequence/")
    PageInfo<StrategySequence> findAll(
            @RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);

    @RequestMapping("/strategysequence/{id}")
    StrategySequenceVO findSequenceByPrimaryKey(@PathVariable("id") Integer id);

    @RequestMapping("/strategysequence/value/{strategyId}")
    String getSequenceValueByStrategyId(@PathVariable("strategyId") Integer strategyId);
}
