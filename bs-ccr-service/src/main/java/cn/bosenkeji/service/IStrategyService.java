package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategyServiceFallbackFactory;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyAttribute;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "bs-ccr-provider-strategy",configuration = FeignClientConfig.class
        //, fallbackFactory = IStrategyServiceFallbackFactory.class
        )
public interface IStrategyService {

    @GetMapping("/strategy/{id}")
    StrategyVO getStrategy(@PathVariable("id") Integer id);

    @GetMapping("/strategy/")
    PageInfo<Strategy>  listByPage(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    @PostMapping("/strategy/")
    Optional<Integer> addStrategyBySelective(Strategy strategy);

    @PostMapping("/strategy/attribute/")
    Optional<Integer> addStrategyAttributeBySelective(StrategyAttribute strategyAttribute);

}
