package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:42
 */
@RestController
@RequestMapping("/product")
@Validated
@Api("产品api接口")
public class ProductController {

    @Resource
    private IProductService iProductService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取产品列表api接口",notes = "获取产品列表api接口")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public List<Product> list() { return this.iProductService.list(); }

    @ApiOperation(value="获取产品详情api接口",notes = "获取产品详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") @Min(1) int id) { return this.iProductService.get(id);}

    @ApiOperation(value="添加产品api接口",notes = "添加产品表api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody Product product) { return this.iProductService.add(product);}

    @ApiOperation(value="根据id删除产品api接口",notes = "根据id删除产品api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") @Min(1) int id) { return this.iProductService.delete(id);}

    @ApiOperation(value="更新产品api接口",notes = "更新产品api接口")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody Product product) { return this.iProductService.update(product);}

    @ApiOperation(value="获取当前服务api接口",notes = "获取当前服务api接口")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}

    @ApiOperation(value="启用、关闭产品api接口",notes = "启用、关闭产品api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public boolean updateStatus(@PathVariable("id") int id,int status) { return this.iProductService.updateStatus(id,status);}



}
