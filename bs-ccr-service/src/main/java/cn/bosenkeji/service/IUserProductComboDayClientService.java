package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserProductComboDayClientServiceFallbackFactory;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProductComboDayClientServiceFallbackFactory.class)
public interface IUserProductComboDayClientService {

    @GetMapping("/user_product_combo_day/list_by_tel/")
    PageInfo listByTel(@RequestParam("tel") String tel, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize);

    @GetMapping("/user_product_combo_day/list_by_user_product_combo_id")
    PageInfo listByUserProductComboId(@RequestParam("userProductComboId") int userProductComboId, @RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize);


    @GetMapping("/user_product_combo_day/")
    PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize);


}
