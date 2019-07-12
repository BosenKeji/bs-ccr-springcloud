package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IProductComboClientServiceFallbackFactory;
import cn.bosenkeji.vo.Coin;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductCombo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ICoinClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-product",configuration = FeignClientConfig.class,fallbackFactory = IProductComboClientServiceFallbackFactory.class)
public interface IProductComboClientService {

    @GetMapping("/productcombo/{id}")
    public ProductCombo getProductCombo(@PathVariable("id") int id);

    @GetMapping("/productcombo/")
    public List<ProductCombo> listProductCombo() ;

    @PostMapping("/productcombo/")
    public boolean addProductCombo(@RequestBody ProductCombo productCombo) ;

    @PutMapping("/productcombo/")
    public boolean updateProductCombo(@RequestBody ProductCombo productCombo);

    @DeleteMapping("/productcombo/{id}")
    public boolean deleteProductCombo(@PathVariable("id") int id);
}
