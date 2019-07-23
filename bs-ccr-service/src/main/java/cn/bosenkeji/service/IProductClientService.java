package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProdcutClientServiceFallbackFactory;
import cn.bosenkeji.vo.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName IProductClientService
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-product",configuration = FeignClientConfig.class,fallbackFactory = IProdcutClientServiceFallbackFactory.class)
public interface IProductClientService {

    @Resource
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") int id);

    @GetMapping("/product/")
    public PageInfo listProduct(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum ,
                                         @RequestParam(value = "pageSize",defaultValue = "15") int pageSize) ;

    @PostMapping("/product/")
    public Optional<Integer> addProduct(@RequestBody Product product) ;

    @PutMapping("/product/")
    public Optional<Integer> updateProduct(@RequestBody Product product);
    @DeleteMapping("/product/{id}")
    public Optional<Integer> deleteProduct(@PathVariable("id") int id);

    @PutMapping("/product/{id}")
    public Optional<Integer> updateProductStatus(@PathVariable("id") int id,@RequestParam("status") int status);
}
