package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.vo.Product;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author xivin
 * @create 2019-07-12 13:39
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerProductController {

    @Resource
    private IProductClientService iProductClientService;


    @GetMapping("/product")
    public Object listProduct() { return this.iProductClientService.listProduct();}

    @GetMapping("/product/{id}")
    public Object getProduct(@PathVariable("id") int id) {
        return this.iProductClientService.getProduct(id);
    }

    @PostMapping("/product")
    public Object addProduct(Product product) {return this.iProductClientService.addProduct(product);}

    @PutMapping("/product")
    public Object updateProduct(Product product) {
        return this.iProductClientService.updateProduct(product);
    }

    @DeleteMapping("/product/{id}")
    public Object deleteProduct(@PathVariable("id") int id) { return  this.iProductClientService.deleteProduct(id);}
}
