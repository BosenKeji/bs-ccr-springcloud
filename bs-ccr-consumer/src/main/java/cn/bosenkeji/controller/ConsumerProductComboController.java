package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductCombo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @create 2019-07-12 13:39
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerProductComboController {

    @Resource
    private IProductComboClientService iProductComboClientService;

    @GetMapping("/productcombo")
    public Object listProductCombo() { return this.iProductComboClientService.listProductCombo();}

    @GetMapping("/productcombo/{id}")
    public Object getProductCombo(@PathVariable("id") int id) {
        return this.iProductComboClientService.getProductCombo(id);
    }

    @PostMapping("/productcombo")
    public Object addProductCombo(ProductCombo productCombo) {return this.iProductComboClientService.addProductCombo(productCombo);}

    @PutMapping("/productcombo")
    public Object updateProductCombo(ProductCombo productCombo) {
        return this.iProductComboClientService.updateProductCombo(productCombo);
    }

    @DeleteMapping("/productcombo/{id}")
    public Object deleteProductCombo(@PathVariable("id") int id) { return  this.iProductComboClientService.deleteProductCombo(id);}
}
