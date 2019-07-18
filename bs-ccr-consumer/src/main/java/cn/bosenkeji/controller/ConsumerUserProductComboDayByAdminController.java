package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboDayByAdminClientService;
import cn.bosenkeji.vo.UserProductComboDayByAdmin;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 20:33 待开发中……
 */
/*@RestController
@RequestMapping("consumer")
@Api("用户时长操作api接口")*/
public class ConsumerUserProductComboDayByAdminController {

    @Resource
    private IUserProductComboDayByAdminClientService iUserProductComboDayByAdminClientService;

    /*@ApiOperation(value = "新增用户时长api接口",httpMethod = "POST")
    @PostMapping("/userproductcombodaybyadmin")*/
    public Object add(@RequestBody UserProductComboDayByAdmin userProductComboDayByAdmin) { return this.iUserProductComboDayByAdminClientService.add(userProductComboDayByAdmin);}
}
