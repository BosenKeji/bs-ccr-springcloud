package cn.bosenkeji.controller;

import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.enums.ProductComboEnum;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    @ApiOperation(value ="获取产品套餐列表api",notes = "获取产品套餐列表api")
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public PageInfo<ProductCombo> list(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize)
    {
        return this.iProductComboService.list(pageNum,pageSize);
    }

    @ApiOperation(value ="获取 未停用|停用 产品套餐列表api",notes = "获取 未停用|停用 产品套餐列表api")
    @RequestMapping(value = "/listbystatus",method = RequestMethod.GET)
    public PageInfo<ProductCombo> listByStatus(@RequestParam(value="pageNum",defaultValue="1") int pageNum, @RequestParam(value="pageSize",defaultValue="15") int pageSize,@RequestParam("status") int status)
    {
        return this.iProductComboService.listByStatus(pageNum,pageSize,status);
    }

    @ApiOperation(value ="获取产品套餐详情api接口",notes = "获取产品套餐详情api接口")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ProductCombo get(@PathVariable("id") @Min(1) int id) {
        return this.iProductComboService.get(id).orElseThrow(()->new NotFoundException(ProductComboEnum.NAME));
    }

    @ApiOperation(value ="获添加品套餐信息api接口",notes = "添加产品套餐信息api接口")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public boolean add(@RequestBody @NotNull ProductCombo productCombo) { return this.iProductComboService.add(productCombo);}

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
