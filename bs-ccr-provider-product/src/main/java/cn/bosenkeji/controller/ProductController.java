package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.ProductEnum;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.Product;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @ApiOperation(value="获取产品列表api接口",notes = "获取产品列表api接口",httpMethod = "GET")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo<Product> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize)
    {
        return this.iProductService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取产品详情api接口",notes = "获取产品详情api接口",httpMethod = "GET")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") @Min(1) int id) { return this.iProductService.get(id).orElseThrow(()->new NotFoundException(ProductEnum.NAME));}

    @ApiOperation(value="添加产品api接口",notes = "添加产品表api接口",httpMethod = "POST")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody Product product) {
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iProductService.add(product);
    }

    @ApiOperation(value="根据id删除产品api接口",notes = "根据id删除产品api接口",httpMethod = "DELETE")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") @Min(1) int id) { return this.iProductService.delete(id);}

    @ApiOperation(value="更新产品api接口",notes = "更新产品api接口",httpMethod = "PUT")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public boolean update(@RequestBody Product product) {
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iProductService.update(product);
    }

    @ApiOperation(value="获取当前服务api接口",notes = "获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    public Object discover() { return this.discoveryClient;}

    @ApiOperation(value="启用、关闭产品api接口",notes = "启用、关闭产品api接口",httpMethod = "PUT")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public boolean updateStatus(@PathVariable("id") @Min(1) int id,@RequestParam("status") int status) {
        Product product=new Product();
        product.setId(id);
        product.setStatus(status);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iProductService.updateStatus(product);
    }



}
