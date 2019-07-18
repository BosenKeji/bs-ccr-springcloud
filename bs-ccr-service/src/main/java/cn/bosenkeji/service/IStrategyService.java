package cn.bosenkeji.service;


import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IStrategySequenceServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IStrategyServiceFallbackFactory;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.StrategyVO;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bs-ccr-provider-strategy",configuration = FeignClientConfig.class,
        fallbackFactory = IStrategyServiceFallbackFactory.class)
public interface IStrategyService {

    @GetMapping("/strategy/{id}")
    StrategyVO getStrategy(@PathVariable("id") Integer id);

    @GetMapping("/strategy/")
    PageInfo<Strategy>  listByPage(
            @RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize);


}
