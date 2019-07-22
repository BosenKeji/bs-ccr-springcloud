package cn.bosenkeji.controller;

import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 18:08
 */
@RestController
@RequestMapping("/consumer")
@Api(tags = "产品套餐api接口",value = "提供产品套餐相关 rest API")
public class ConsumerProductComboController {

    @Resource
    private IProductComboClientService iProductComboClientService;

    @ApiOperation(value = "通过productId查询产品套餐列表api接口",httpMethod = "GET")
    @GetMapping("/listbyproductid")
    public Object listByProductId(@RequestParam("productId") int productId)
    {
        return this.iProductComboClientService.listByProductId(productId);
    }

    @ApiOperation(value = "获取产品套餐详情api接口",httpMethod = "GET")
    @GetMapping("/productcombo/{id}")
    public Object get(@PathVariable("id") int id) { return this.iProductComboClientService.get(id);}

    @ApiOperation(value = "新增产品套餐api接口",httpMethod = "POST")
    @PostMapping("/productcombo")
    public Object add(@RequestBody ProductCombo productCombo) { return this.iProductComboClientService.add(productCombo);}

    @ApiOperation(value="更新产品套餐api接口",httpMethod = "PUT")
    @PutMapping("/productcombo")
    public Object update(@RequestBody ProductCombo productCombo) { return this.iProductComboClientService.update(productCombo);}

    @ApiOperation(value = "删除产品套餐api接口",httpMethod = "DELETE")
    @DeleteMapping("/productcombo/{id}")
    public Object delete(@PathVariable("id") int id) { return this.iProductComboClientService.delete(id);}

    @ApiOperation(value = "启动|关闭 产品套餐api接口",notes = "启动为1 关闭为2",httpMethod = "PUT")
    @PutMapping("/productcombo/{id}")
    public Object updateByStatus(@PathVariable("id") int id,@RequestParam("status") int status)
    {
        return this.iProductComboClientService.updateByStatus(id,status);
    }
}
