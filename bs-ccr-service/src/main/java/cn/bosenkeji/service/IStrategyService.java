package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategyServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.Strategy;
import cn.bosenkeji.vo.strategy.StrategyAttribute;
import cn.bosenkeji.vo.strategy.StrategyOther;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "bs-ccr-provider-strategy",configuration = FeignClientConfig.class
        //, fallbackFactory = IStrategyServiceFallbackFactory.class
        )
public interface IStrategyService {

    @GetMapping("/strategy/{id}")
    StrategyOther getStrategy(@PathVariable("id") Integer id);

    @GetMapping("/strategy/")
    PageInfo<Strategy>  listByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/strategy/")
    Result addStrategyBySelective(Strategy strategy);

    @PostMapping("/strategy/attribute/")
    Result addStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

}
