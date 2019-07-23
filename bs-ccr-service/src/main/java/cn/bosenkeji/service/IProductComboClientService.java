package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProdcutComboClientServiceFallbackFactory;
import cn.bosenkeji.vo.ProductCombo;
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
@FeignClient(name = "bs-ccr-provider-combo",configuration = FeignClientConfig.class,fallbackFactory = IProdcutComboClientServiceFallbackFactory.class)
public interface IProductComboClientService {

    @GetMapping(value = "/product_combo/list_by_product_id/")
    PageInfo listByProductId(@RequestParam("productId") int productId);


    @PostMapping("/product_combo/")
    Optional<Integer> add(@RequestBody ProductCombo productCombo);

    @PutMapping("/product_combo/")
    Optional<Integer> update(@RequestBody ProductCombo productCombo);

    @DeleteMapping("/product_combo/{id}")
    Optional<Integer> delete(@PathVariable("id") int id);

    @GetMapping("/product_combo/{id}")
    Optional get(@PathVariable("id") int id);

    @PutMapping("/product_combo/{id}")
    Optional<Integer> updateByStatus(@PathVariable("id") int id,@RequestParam("status") int status);

}
