package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.vo.combo.ProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 18:08
 */
@RestController
@RequestMapping("/product_combo")
@Api(tags = "ProductCombo产品套餐接口",value = "提供产品套餐相关 Rest API")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ConsumerProductComboController {

    @Resource
    private IProductComboClientService iProductComboClientService;

    @ApiOperation(value ="根据产品id获取产品套餐列表",httpMethod = "GET",nickname = "getProductComboListByProductIdWithPage")
    @GetMapping("/list_by_product_id")
    public Object listByProductId(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                  @RequestParam("productId") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int productId)
    {
        return this.iProductComboClientService.listByProductId(productId);
    }

    @ApiOperation(value ="根据产品id获取未停用|停用的产品套餐列表",httpMethod = "GET",nickname = "getProductComboListByProductIdAndStatusWithPage")
    @RequestMapping(value = "/list_by_product_id_and_status",method = RequestMethod.GET)
    public PageInfo listByProductIdAndStatus(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                             @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                             @RequestParam("productId") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int productId,
                                             @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status) {

        return this.iProductComboClientService.listByProductIdAndStatus(pageNum,pageSize,productId,status);
    }

    @ApiOperation(value ="根据产品id获取 未停用 的产品套餐列表",httpMethod = "GET",nickname = "getProductComboListByProductIdAndOpenWithPage")
    @RequestMapping(value = "/list_by_product_id_and_open",method = RequestMethod.GET)
    public PageInfo listByProductIdAndOpen(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                             @RequestParam(value="pageSize",defaultValue="15") int pageSize,
                                             @RequestParam("productId") @ApiParam(value = "产品ID",required = true,type = "integer",example = "1") int productId) {

        int status=1;
        return this.iProductComboClientService.listByProductIdAndStatus(pageNum,pageSize,productId,status);
    }



    @ApiOperation(value ="获取产品套餐详情接口",httpMethod = "GET",nickname = "getOneProductCombo")
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) {
        return this.iProductComboClientService.get(id);
    }

    @ApiOperation(value ="获添加品套餐信息接口",httpMethod = "POST",nickname = "addProductCombo")
    @PostMapping("/")
    public Object add(@RequestBody @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        return this.iProductComboClientService.add(productCombo);
    }

    @ApiOperation(value ="更新产品套餐信息接口",httpMethod = "PUT",nickname = "updateProductCombo")
    @PutMapping("/")
    public Object update(@RequestBody @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        return this.iProductComboClientService.update(productCombo);
    }

    @ApiOperation(value ="删除产品套餐信息接口",httpMethod = "DELETE",nickname = "deleteProductComboInfo")
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable("id") @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) { return this.iProductComboClientService.delete(id);}

    @ApiOperation(value ="启用、关闭产品套餐api接口",httpMethod = "PUT",nickname = "updateProductComboStatus")
    @PutMapping("/{id}")
    public Object updateByStatus(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id,
                                 @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status)
    {
        return this.iProductComboClientService.updateByStatus(id,status);
    }

    @ApiOperation(value ="启用产品套餐api接口",httpMethod = "PUT",nickname = "openProductComboStatus")
    @PutMapping("/open/{id}")
    public Object openCombo(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id)
    {
        int status=1;
        return this.iProductComboClientService.updateByStatus(id,status);
    }

    @ApiOperation(value ="关闭产品套餐api接口",httpMethod = "PUT",nickname = "closeProductComboStatus")
    @PutMapping("/close/{id}")
    public Object closeCombo(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id)
    {
        int status=2;
        return this.iProductComboClientService.updateByStatus(id,status);
    }

}
