package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformClientServiceFallbackFactory;
import cn.bosenkeji.vo.tradeplateform.TradePlatform;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:36
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformClientServiceFallbackFactory.class)
public interface ITradePlatformClientService {
    @GetMapping("/trade_platform/")
    public PageInfo listTradePlatformWithPage(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);
    @GetMapping("/trade_platform/{id}")
    public TradePlatform getOneTrdPatform(@PathVariable("id") int id);

    @PostMapping("/trade_platform/")
    public boolean addOneTradePlatform(@RequestBody TradePlatform tradePlatform);

    @PutMapping("/trade_platform/")
    public boolean updateTradePlatform(@RequestBody TradePlatform tradePlatform);

    @DeleteMapping("/trade_platform/{id}")
    public boolean deleteOneTradePlatform(@PathVariable("id") int id);

}
