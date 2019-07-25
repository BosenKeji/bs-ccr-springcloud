package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.vo.product.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xivin
 * @create 2019-07-12 13:39
 */
@RestController
@RequestMapping("/product")
@Api(tags = "Prodcut 产品相关接口",value = "提供产品相关的 Rest API")
public class ConsumerProductController {

    @Resource
    private IProductClientService iProductClientService;


    @ApiOperation(value="获取产品列表api接口",httpMethod = "GET",nickname = "getProductListWithPage")
    @GetMapping("/")
    public Object listProduct(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                              @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iProductClientService.listProduct(pageNum,pageSize);
    }


    @ApiOperation(value="获取产品详情api接口",httpMethod = "GET",nickname = "getOneProduct")
    @GetMapping("/{id}")
    public Object getProduct(@PathVariable("id") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id) {
        return this.iProductClientService.getProduct(id);
    }

    @ApiOperation(value="添加产品api接口",httpMethod = "POST",nickname = "addProduct")
    @PostMapping("/")
    public Object addProduct(@RequestBody @ApiParam(value = "产品实体",required = true,type = "string") Product product) {return this.iProductClientService.addProduct(product);}

    @ApiOperation(value="更新产品api接口",httpMethod = "PUT",nickname = "updateProduct")
    @PutMapping("/")
    public Object updateProduct(@RequestBody @ApiParam(value = "产品实体",required = true,type = "string") Product product) {
        return this.iProductClientService.updateProduct(product);
    }

    @ApiOperation(value="启用、关闭产品api接口",httpMethod = "PUT",nickname = "updateProductStatus")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Object updateProductStatus(@PathVariable("id") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id,
                                                 @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status) {

        return this.iProductClientService.updateProductStatus(id,status);
    }

        @ApiOperation(value="根据id删除产品api接口",httpMethod = "DELETE",nickname = "deleteOneProduct")
    @DeleteMapping("/{id}")
    public Object deleteProduct(@PathVariable("id") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int id) { return  this.iProductClientService.deleteProduct(id);}
}
