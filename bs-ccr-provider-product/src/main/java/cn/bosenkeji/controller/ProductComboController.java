package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductCombo;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:45
 */
@RestController
@RequestMapping("/productcombo")
@Validated
@Api("产品套餐api接口")
public class ProductComboController {

    @Resource
    private IProductComboService iProductComboService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value ="获取产品套餐列表api接口",notes = "获取产品套餐列表api接口")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public List<ProductCombo> list() { return this.iProductComboService.list();}

    @ApiOperation(value ="获取产品套餐详情api接口",notes = "获取产品套餐详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ProductCombo get(@PathVariable("id") @Min(1) int id) { return this.iProductComboService.get(id);}

    @ApiOperation(value ="获添加品套餐信息api接口",notes = "添加产品套餐信息api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody ProductCombo productCombo) { return this.iProductComboService.add(productCombo);}

    @ApiOperation(value ="更新产品套餐信息api接口",notes = "更新产品套餐信息api接口")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody ProductCombo productCombo) { return this.iProductComboService.update(productCombo);}

    @ApiOperation(value ="删除产品套餐信息api接口",notes = "删除产品套餐信息api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") int id) { return this.iProductComboService.delete(id);}

    @ApiOperation(value ="获取当前服务api接口",notes = "获取当前服务api接口")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}

    @ApiOperation(value ="启用、关闭产品套餐api接口",notes = "启用、关闭产品套餐api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public boolean updateStatus(@PathVariable("id") @Min(1) int id, int status) {
        ProductCombo productCombo=new ProductCombo();
        productCombo.setId(id);
        productCombo.setStatus(status);
        return this.iProductComboService.update(productCombo);
    }
}
