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

    @GetMapping(value = "/productcombo/listbyproductid/")
    PageInfo<ProductCombo> listByProductId(@RequestParam("productId") int productId);


    @PostMapping("/productcombo/")
    boolean add(@RequestBody ProductCombo productCombo);

    @PutMapping("/productcombo/")
    boolean update(@RequestBody ProductCombo productCombo);

    @DeleteMapping("/productcombo/{id}")
    boolean delete(@PathVariable("id") int id);

    @GetMapping("/productcombo/{id}")
    Optional<ProductCombo> get(@PathVariable("id") int id);

    @PutMapping("/productcombo/{id}")
    boolean updateByStatus(@PathVariable("id") int id,@RequestParam("status") int status);

}
