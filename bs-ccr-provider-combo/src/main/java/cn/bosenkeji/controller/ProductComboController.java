package cn.bosenkeji.controller;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.ProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
 * @create 2019-07-11 11:45
 */
@RestController
@RequestMapping("/product_combo")
@Validated
@Api(tags = "ProductCombo 产品套餐相关接口",value="提供产品套餐相关的 Rest API")
@CacheConfig(cacheNames = "ccr:productCombo")
public class ProductComboController {

    @Resource
    private IProductComboService iProductComboService;

    @Resource
    private IUserProductComboService iUserProductComboService;



    @Resource
    private DiscoveryClient discoveryClient;

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,key = "#pageNum+'-'+#pageSize")
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

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,key = "#productId+'-'+#status+'-'+#pageNum+'-'+#pageSize")
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

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#id",unless = "#result==null")
    @ApiOperation(value ="获取产品套餐详情api接口",httpMethod = "GET",nickname = "getOneProductCombo")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public ProductCombo get(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) {
        return this.iProductComboService.get(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true)
            }
    )
    @ApiOperation(value ="获添加品套餐信息api接口",httpMethod = "POST",nickname = "addProductCombo")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Result add(@RequestBody @Valid @NotNull @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        //判断套餐时长是否合法
        if(productCombo.getTime()<1)
            return new Result(0,"套餐时间必须大于等于1");
        //判断 产品套餐名称是否存在
        if(this.iProductComboService.checkExistByNameAndProductId(productCombo.getName(),productCombo.getProductId())>=1)
            return new Result(0,"产品套餐已存在");
               /* .filter((value)->value==0)
                .orElseThrow(()->new AddException(ProductComboEnum.NAME));*/

        productCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        productCombo.setStatus(1);
        return new Result(this.iProductComboService.add(productCombo));

    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#productCombo.id"),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true)
            }
    )
    @ApiOperation(value ="更新产品套餐信息api接口",httpMethod = "PUT",nickname = "updateProductCombo")
    @RequestMapping(value="/",method = RequestMethod.PUT)
    public Result update(@RequestBody @ApiParam(value = "产品套餐实体",required = true,type = "string") ProductCombo productCombo) {
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(this.iProductComboService.update(productCombo));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true)
            }
    )
    @ApiOperation(value ="删除产品套餐信息api接口",httpMethod = "DELETE",nickname = "deleteProductComboInfo")
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id) {
        if(iUserProductComboService.checkExistByProductComboId(id)>=1) {
            return new Result(0,"删除失败，该套餐有用户使用");
        }
        return new Result(this.iProductComboService.delete(id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true)
            }
    )
    @ApiOperation(value ="启用、关闭产品套餐api接口",httpMethod = "PUT",nickname = "updateProductComboStatus")
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Result updateStatus(@PathVariable("id") @Min(1) @ApiParam(value = "产品套餐ID",required = true,type = "integer",example = "1") int id,
                                          @RequestParam("status") @ApiParam(value = "产品状态",required = true,type = "integer",example = "1") int status) {
        ProductCombo productCombo=new ProductCombo();
        productCombo.setId(id);
        productCombo.setStatus(status);
        productCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result(this.iProductComboService.update(productCombo));
    }

    @ApiOperation(value ="获取当前服务api接口",notes = "获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
