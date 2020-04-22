package cn.bosenkeji.controller;

import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-18 19:47
 */
@RestController
@RequestMapping("/user_product_combo")
@Api(tags = "UserProductCombo 用户套餐api接口",value = "提供用户套餐相关的 rest API")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@Validated
public class ConsumerUserProductComboController {

    @Resource
    private IUserProductComboClientService iUserProductComboClientService;


    //@Hmily
    @ApiOperation(value="添加用户套餐信息api接口",httpMethod = "POST",nickname = "addUserProductCombo")
    @PostMapping("/")
    public Result add(@RequestBody @ApiParam(value = "用户套餐实体",required = true,type = "string") @Valid UserProductCombo userProductCombo
                      ) {
        return this.iUserProductComboClientService.add(userProductCombo);
    }

    @ApiOperation(value="查询用户套餐列表接口",httpMethod = "GET",nickname = "getUserProductComboListWithPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {
        return this.iUserProductComboClientService.listByPage(pageNum,pageSize);
    }

    @ApiOperation(value="根据用户电话查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserTelWithPage")
    @GetMapping("/list_by_user_tel")
    public PageInfo listByUserTel(@RequestParam("userTel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String userTel,
                                  @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                  @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {
        return this.iUserProductComboClientService.listByUserTel(userTel,pageNum,pageSize);
    }

    @ApiOperation(value="根据用户电话和id查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserTelAndIdWithPage")
    @GetMapping("/list_by_user_tel_and_id")
    public PageInfo listByUserTelAndId(@RequestParam(value = "userTel",defaultValue = "") @ApiParam(value = "用户电话",type = "string",example = "13556559840") String userTel,
                                     @RequestParam(value = "id",defaultValue = "0") @ApiParam(value = "用户套餐ID",type = "integer",example = "1") int id,
                                @RequestParam(value="pageNum",defaultValue = "1") int pageNum,
                                @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {

        return this.iUserProductComboClientService.listByUserTelAndId(userTel,id,pageNum,pageSize);
    }

    @ApiOperation(value="根据用户Id查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserIdWithPage")
    @RequestMapping(value="/list_by_user_id",method = RequestMethod.GET)
    public PageInfo listByUserId(@RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId,
                                 @RequestParam(value="pageNum",defaultValue = "1") int pageNum, @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {

        return this.iUserProductComboClientService.listByUserId(userId,pageNum,pageSize);
    }

    @ApiOperation(value = "删除用户套餐",httpMethod = "DELETE",nickname = "deleteUserProductCombo")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int id) {

        return this.iUserProductComboClientService.delete(id);
    }

    @ApiOperation(value = "更新运行状态接口",notes = "1 运行中 2 已暂停 3 已封禁", httpMethod = "PUT",nickname = "updateUserProductComboRunStatus")
    @PutMapping("/run_status")
    public Result<Integer> updateRunStatus(@RequestParam("id") @ApiParam(value = "用户套餐id",required = true,type = "integer",example = "1") int id,
                                           @RequestParam("runStatus") @ApiParam(value = "运行状态",required = true,type = "integer",example = "1") int runStatus) {
        return iUserProductComboClientService.updateRunStatus(id,runStatus);
    }


}
