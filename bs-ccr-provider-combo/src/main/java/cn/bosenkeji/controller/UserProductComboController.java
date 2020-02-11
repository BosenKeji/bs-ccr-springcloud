package cn.bosenkeji.controller;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.controller
 * @Version V1.0
 * @create 2019-07-15 11:15
 */

import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.service.IUserProductComboService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.ComboDOTransformToVOUtil;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboVO;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import scala.Int;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user_product_combo")
@Validated
@Api(tags = "UserProductCobmo 用户套餐相关接口",value="提供用户套餐相关的 Rest API")
public class UserProductComboController {

    @Resource
    private IUserProductComboService iUserProductComboService;

    @Resource
    private IProductComboService iProductComboService;

    @Resource
    private IUserClientService iUserClientService;

    @Resource
    private DiscoveryClient discoveryClient;


    @ApiOperation(value="获取用户套餐列表api接口",httpMethod = "GET",nickname = "getUserProductComboListWithPage")
    @RequestMapping(value="/",method = RequestMethod.GET)
    public PageInfo list(@RequestParam(value="pageNum",defaultValue = "1") int pageNum, @RequestParam(value="pageSize",defaultValue = "15") int pageSize)
    {
        PageInfo userProductComboPageInfo = this.iUserProductComboService.list(pageNum, pageSize);
        if(null != userProductComboPageInfo.getList() && userProductComboPageInfo.getList().size() > 0) {
            userProductComboPageInfo.setList(ComboDOTransformToVOUtil.transformUserProductComboDOToVO(userProductComboPageInfo.getList()));
        }
        return userProductComboPageInfo;
    }

    @ApiOperation(value="获取用户套餐详情api接口",httpMethod = "GET",nickname = "getOneUserProductCombo")
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public UserProductCombo get(@PathVariable("id") @Min(1) @ApiParam(value = "用户套餐ID",required = true,type = "integer",example = "1") int id) {
        return this.iUserProductComboService.get(id);
    }

    @ApiOperation(value="添加用户套餐信息api接口",httpMethod = "POST",nickname = "addUserProductCombo")
    @RequestMapping(value="/",method = RequestMethod.POST)
    public Result add(@RequestBody @Valid @NotNull @ApiParam(value = "用户套餐实体",required = true,type = "string") UserProductCombo userProductCombo
                      ) {

        Result result = iUserClientService.checkExistById(userProductCombo.getUserId());
        Integer isExist=(Integer) result.getData();
        if(isExist==null || isExist<1)
            return new Result(0,"用户不存在，机器人部署失败");
        if(iProductComboService.get(userProductCombo.getProductComboId())==null)
            return new Result(0,"产品套餐不存在，机器人部署失败！");
        //判断用户是否没过该产品
        /*if(this.iUserProductComboService.checkExistByProductIdAndUserId(userProductCombo.getProductComboId(),userProductCombo.getUserId())>=1)
            return new Result(0,"该用户不能重复买该产品");*/
                /*.filter((value)->value==0)
                .orElseThrow(()->new AddException(UserProductComboEnum.NAME));*/
        userProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        userProductCombo.setStatus(1);
        return new Result(this.iUserProductComboService.add(userProductCombo));
    }

    @ApiOperation(value="根据用户电话查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserTelWithPage")
    @RequestMapping(value="/list_by_user_tel",method = RequestMethod.GET)
    public PageInfo<UserProductComboVO> listByUserTel(@RequestParam("userTel") @ApiParam(value = "用户电话",required = true,type = "string",example = "13556559840") String userTel,
                                                      @RequestParam(value="pageNum",defaultValue = "1") int pageNum, @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {

        PageInfo userProductComboPageInfo = this.iUserProductComboService.selectUserProductComboByUserTel(pageNum, pageSize, userTel);
        if(null != userProductComboPageInfo.getList() && userProductComboPageInfo.getList().size() > 0) {
            userProductComboPageInfo.setList(ComboDOTransformToVOUtil.transformUserProductComboDOToVO(userProductComboPageInfo.getList()));
        }
        return userProductComboPageInfo;
    }

    @ApiOperation(value="根据用户Id查询用户套餐api接口",httpMethod = "GET",nickname = "getUserProductComboByUserIdWithPage")
    @RequestMapping(value="/list_by_user_id",method = RequestMethod.GET)
    public PageInfo<UserProductComboVO> listByUserId(@RequestParam("userId") @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int userId,
                                  @RequestParam(value="pageNum",defaultValue = "1") int pageNum, @RequestParam(value="pageSize",defaultValue = "15") int pageSize) {

        PageInfo userProductComboPageInfo = this.iUserProductComboService.selectUserProductComboByUserId(pageNum, pageSize, userId);
        if(null != userProductComboPageInfo.getList() && userProductComboPageInfo.getList().size() > 0) {
            userProductComboPageInfo.setList(ComboDOTransformToVOUtil.transformUserProductComboDOToVO(userProductComboPageInfo.getList()));
        }
        return userProductComboPageInfo;
    }

    @GetMapping("/list_by_ids")
    public Map<Integer,UserProductCombo> listByPrimaryKeys(@RequestParam("ids") List<Integer> ids) {
        return iUserProductComboService.selectByPrimaryKeys(ids);
    }

    @ApiOperation(value = "删除用户套餐",httpMethod = "DELETE",nickname = "deleteUserProductCombo")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") int id) {
        return this.iUserProductComboService.delete(id);
    }


    @ApiOperation(value = "刷新 全部用户套餐时长",httpMethod = "PUT",nickname = "flushAllComboDay")
    @PutMapping("/flush_all_combo_day")
    public Result flushAllComboDay() {
        return new Result(this.iUserProductComboService.flushAllComboDay());
    }

    @ApiOperation(value = "根据userId检查 套餐是否存在",httpMethod = "GET",nickname = "checkExistByUserId")
    @GetMapping("/exist_by_id_and_user_id")
    public Result<Integer> checkExistByUserId(@RequestParam("id") int id,
                                              @RequestParam("userId") int userId) {
        return new Result<>(this.iUserProductComboService.checkExistByIdAndUserId(id,userId));
    }

    //更新用户套餐 运行状态
    @ApiOperation(value = "更新运行状态接口",httpMethod = "PUT",nickname = "updateUserProductComboRunStatus")
    @PutMapping("/run_status")
    public Result<Integer> updateRunStatus(@RequestParam("id") int id, @RequestParam("runStatus") int runStatus) {
        return iUserProductComboService.updateRunStatus(id,runStatus);
    }


    @ApiOperation(value="获取当前服务api接口",notes="获取当前服务api接口",httpMethod = "GET")
    @RequestMapping(value="/discover")
    @ApiIgnore
    public Object discover() { return this.discoveryClient;}
}
