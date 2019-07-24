package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @ClassName IProductClientService
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-product",configuration = FeignClientConfig.class,fallbackFactory = IProductClientServiceFallbackFactory.class)
public interface IProductClientService {

    @Resource
    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable("id") int id);

    @GetMapping("/product/")
    public PageInfo listProduct(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum ,
                                         @RequestParam(value = "pageSize",defaultValue = "15") int pageSize) ;

    @PostMapping("/product/")
    public Result addProduct(@RequestBody Product product) ;

    @PutMapping("/product/")
    public Result updateProduct(@RequestBody Product product);
    @DeleteMapping("/product/{id}")
    public Result deleteProduct(@PathVariable("id") int id);

    @PutMapping("/product/{id}")
    public Result updateProductStatus(@PathVariable("id") int id,@RequestParam("status") int status);
}
