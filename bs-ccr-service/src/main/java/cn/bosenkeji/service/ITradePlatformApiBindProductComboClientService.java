package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiBindProductComboClientServiceFallbackFactory;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 19:48
 */
@FeignClient(name = "bs-ccr-provider-tradePlatform",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiBindProductComboClientServiceFallbackFactory.class)
public interface ITradePlatformApiBindProductComboClientService {

    @GetMapping("/trade_platform_api_bind_product_combo/list_by_user_id/")
    public PageInfo getListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                    @RequestParam("userId") int userId);

    @PostMapping("/trade_platform_api_bind_product_combo/")
    public Optional<Integer> addTradePlatformApiBindProductCombo(@RequestBody TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);
}
