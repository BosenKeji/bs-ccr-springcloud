package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.ProductCombo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:45
 */
@RestController
@RequestMapping("/productcombo")
public class ProductComboController {

    @Resource
    private IProductComboService iProductComboService;

    @RequestMapping(value = "/")
    public List<ProductCombo> list() { return this.iProductComboService.list();}

    @RequestMapping(value="/{id}")
    public ProductCombo get(@PathVariable("id") int id) { return this.iProductComboService.get(id);}

    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody ProductCombo productCombo) { return this.iProductComboService.add(productCombo);}

    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean put(@RequestBody ProductCombo productCombo) { return this.iProductComboService.update(productCombo);}

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") int id) { return this.iProductComboService.delete(id);}
}
