package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ITradePlatformApiBindProductComboClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-26 19:48
 */
@FeignClient(name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ITradePlatformApiBindProductComboClientServiceFallbackFactory.class)
public interface ITradePlatformApiBindProductComboClientService {

    @GetMapping("/trade_platform_api_bind_product_combo/list_by_user_id/")
    public PageInfo getListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                    @RequestParam("userId") int userId);
    @GetMapping("/trade_platform_api_bind_product_combo/get_no_bind_trade_platform_api_list_by_user_id/")
    public PageInfo getNoBindTradePlatformApiListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                    @RequestParam("userId") int userId);
   /* @GetMapping("/trade_platform_api_bind_product_combo/get_no_bind_user_product_combo_list_by_user_id/")
    public PageInfo getNoBindUserProductComboListByUserId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                    @RequestParam("userId") int userId);*/


    @PostMapping("/trade_platform_api_bind_product_combo/")
    Result addTradePlatformApiBindProductCombo(@RequestBody TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo);

    @PutMapping("/trade_platform_api_bind_product_combo/{id}")
    Result updateTradePlatformApiBindProductCombo(@PathVariable("id") int id
            ,@RequestParam("tradePlatformApiId") int tradePlatformApiId
            ,@RequestParam("userId") int userId);

    @DeleteMapping("/trade_platform_api_bind_product_combo/{id}")
    Result deleteTradePlatformApiBindProductCombo(@PathVariable("id") int id
            ,@RequestParam("userId") int userId);

    @DeleteMapping("/trade_platform_api_bind_product_combo/real/{id}")
    public Result realDelete(@PathVariable("id") int id);

    @DeleteMapping("/trade_platform_api_bind_product_combo/by_combo/{userProductComboId}")
    public Result deleteByComboId(@PathVariable("userProductComboId") int userProductComboId);

    @GetMapping("trade_platform_api_bind_product_combo/")
    public List findAll();

    @GetMapping("trade_platform_api_bind_product_combo/{id}")
    public int getUserIdById(@PathVariable("id") int id);

}
