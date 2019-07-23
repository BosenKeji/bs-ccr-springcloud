package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.ProductComboEnum;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:45
 */
@RestController
@RequestMapping("/product_combo")
@Validated
@Api(tags = "PrdocutCombo 产品套餐相关接口",value="提供产品套餐相关的 Rest API")
public class ProductComboController {

    @Resource
    private IProductComboService iProductComboService;

    @Resource
    private DiscoveryClient discoveryClient;

    @ApiOperation(value ="获取产品套餐列表api",notes = "获取产品套餐列表api",httpMethod = "GET",nickname = "getProductComboListWithPage")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue="15") int pageSize)
    {
        return this.iProductComboService.list(pageNum,pageSize);
    }

    @ApiOperation(value ="根据产品id获取产品套餐列表api",httpMethod = "GET",nickname = "getProductComboListByProductIdWithPage")
    @RequestMapping(value = "/list_by_product_id",method = RequestMethod.GET)
    public PageInfo listByProductId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                    @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                    @RequestParam("productId") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int productId)
    {
        return this.iProductComboService.listByProductId(pageNum,pageSize,productId);
    }

    @ApiOperation(value ="根据产品id获取未停用|停用的产品套餐列表api",httpMethod = "GET",nickname = "getProductComboListByProductIdAndStatusWithPage")
    @RequestMapping(value = "/list_by_product_id_and_status",method = RequestMethod.GET)
    public PageInfo listByProductIdAndStatus(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                             @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                             @RequestParam("productId") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int productId,
                                             @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status)
    {
        return this.iProductComboService.listByProductIdAndStauts(pageNum,pageSize,productId,status);
    }


    @ApiOperation(value ="获取 未停用|停用 产品套餐列表api",httpMethod = "GET",nickname = "getProductComboListByStatusWithPage")
    @RequestMapping(value = "/list_by_status",method = RequestMethod.GET)
    public PageInfo listByStatus(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                 @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                 @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status)
    {
        return this.iProductComboService.listByStatus(pageNum,pageSize,status);
    }

    @ApiOperation(value ="获取产品套餐详情api接口",httpMethod = "GET",nickname = "getProductComboDetail")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ProductCombo get(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) {
        return this.iProductComboService.get(id).orElseThrow(()->new NotFoundException(ProductComboEnum.NAME));
    }

    @ApiOperation(value ="获添加品套餐信息api接口",httpMethod = "POST",nickname = "addProductComboInfo")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Optional<Integer> add(@RequestBody @NotNull @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        productCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productCombo.setStatus(1);
        return this.iProductComboService.add(productCombo);

    }

    @ApiOperation(value ="更新产品套餐信息api接口",httpMethod = "PUT",nickname = "updateProductComboInfo")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public Optional<Integer> update(@RequestBody @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iProductComboService.update(productCombo);
    }

    @ApiOperation(value ="删除产品套餐信息api接口",httpMethod = "DELETE",nickname = "deleteProductComboInfo")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Optional<Integer> delete(@PathVariable("id") @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) { return this.iProductComboService.delete(id);}

    @ApiOperation(value ="启用、关闭产品套餐api接口",httpMethod = "PUT",nickname = "updateProductComboStatus")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Optional<Integer> updateStatus(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id,
                                          @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status) {
        ProductCombo productCombo=new ProductCombo();
        productCombo.setId(id);
        productCombo.setStatus(status);
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return this.iProductComboService.update(productCombo);
    }

    @ApiOperation(value ="获取当前服务api接口",notes = "获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
