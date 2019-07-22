package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformClientServiceFallbackFactory;
import cn.bosenkeji.vo.TradePlatform;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author CAJR
 * @create 2019/7/22 10:36
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformClientServiceFallbackFactory.class)
public interface ITradePlatformClientService {
    @GetMapping("/tradeplatform/")
    public PageInfo listTradePlatformWithPage(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon);
    @GetMapping("/tradeplatform/{id}")
    public TradePlatform getOneTrdPatform(@PathVariable("id") int id);

    @PostMapping("/tradeplatform/")
    public boolean addOneTradePlatform(@RequestBody TradePlatform tradePlatform);

    @PutMapping("/tradeplatform/")
    public boolean updateTradePlatform(@RequestBody TradePlatform tradePlatform);

    @DeleteMapping("/tradeplatform/{id}")
    public boolean deleteOneTradePlatform(@PathVariable("id") int id);

}