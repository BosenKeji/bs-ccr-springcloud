package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author xivin
 * @create 2019-07-11 11:42
 */
@RestController
@RequestMapping("/product")
@Validated
@Api(tags = "Product 产品相关接口",value="提供产品相关的 Rest API")
public class ProductController {

    @Resource
    private IProductService iProductService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value="获取产品列表api接口",httpMethod = "GET",nickname = "getProductListWithPage")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="15") int pageSize)
    {
        return this.iProductService.list(pageNum,pageSize);
    }

    @ApiOperation(value="获取产品详情api接口",httpMethod = "GET",nickname = "getOneProduct")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") @Min(1) @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id) {
        return this.iProductService.get(id);
    }

    @ApiOperation(value="添加产品api接口",httpMethod = "POST",nickname = "addProduct")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Result add(@RequestBody @Valid @NotNull @ApiParam(value = "产品实体",required = true,type = "string") Product product) {
       if(this.iProductService.checkExistByName(product.getName())==1)
           return new Result("0","产品名称已存在");
        product.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        product.setStatus(1);
        return new Result(this.iProductService.add(product));
    }

    @ApiOperation(value="根据id删除产品api接口",httpMethod = "DELETE",nickname = "deleteOneProduct")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") @Min(1)
                                        @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id) {
        return new Result(this.iProductService.delete(id));
    }

    @ApiOperation(value="更新产品api接口",httpMethod = "PUT",nickname = "updateProduct")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public Result update(@RequestBody @ApiParam(value = "产品实体",required = true,type = "string") Product product) {
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(this.iProductService.update(product));
    }

    @ApiOperation(value="启用、关闭产品api接口",httpMethod = "PUT",nickname = "updateProductStatus")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Result updateProductStatus(@PathVariable("id") @Min(1) @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id,@RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status) {

        Product product=new Product();
        product.setId(id);
        product.setStatus(status);
        product.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(this.iProductService.updateStatus(product));
    }


    @ApiOperation(value="获取当前服务api接口",notes = "获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}



}
