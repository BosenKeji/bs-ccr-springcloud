package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductComboClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.ProductCombo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-combo",configuration = FeignClientConfig.class,fallbackFactory = IProductComboClientServiceFallbackFactory.class)
public interface IProductComboClientService {

    @GetMapping(value = "/product_combo/list_by_product_id/")
    PageInfo listByProductId(@RequestParam("productId") int productId);


    @PostMapping("/product_combo/")
    Result add(@RequestBody ProductCombo productCombo);

    @PutMapping("/product_combo/")
    Optional<Integer> update(@RequestBody ProductCombo productCombo);

    @DeleteMapping("/product_combo/{id}")
    Optional<Integer> delete(@PathVariable("id") int id);

    @GetMapping("/product_combo/{id}")
    ProductCombo get(@PathVariable("id") int id);

    @PutMapping("/product_combo/{id}")
    Optional<Integer> updateByStatus(@PathVariable("id") int id,@RequestParam("status") int status);

    @GetMapping(value = "/product_combo/list_by_product_id_and_status")
    public PageInfo listByProductIdAndStatus(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                             @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                             @RequestParam("productId")int productId,
                                             @RequestParam("status")  int status);


}
