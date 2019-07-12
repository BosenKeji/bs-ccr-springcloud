package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:42
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService iProductService;

    @RequestMapping(value="/")
    public List<Product> list() { return this.iProductService.list(); }

    @RequestMapping(value="/{id}")
    public Product get(@PathVariable("id") int id) { return this.iProductService.get(id);}

    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody Product product) { return this.iProductService.add(product);}

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer id) { return this.iProductService.delete(id);}

    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody Product product) { return this.iProductService.update(product);}

}
