/**
 * FileName: ConsumerComboDayReasonController
 * Author: xivin
 * Date: 2019-09-23 10:59
 * Description:
 */
package cn.bosenkeji.controller;

import cn.bosenkeji.service.IComboDayReasonClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.ComboDayReason;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/combo_day_reason")
@Api(tags = "ComboDayReason 套餐时长事由相关接口",value="提供套餐时长事由相关的 Rest API")
public class ConsumerComboDayReasonController {

    @Resource
    private IComboDayReasonClientService iComboDayReasonClientService;

    @ApiOperation(value = "获取事由列表接口",httpMethod = "GET",nickname = "getComboDayReasonListWithPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value="pageSize",defaultValue="15") int pageSize) {
        return this.iComboDayReasonClientService.listByPage(pageNum,pageSize);
    }

    @ApiOperation(value = "获取一个事由接口",httpMethod = "GET",nickname = "getOneComboDayReason")
    @GetMapping("/{id}")
    public ComboDayReason getOne(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") int id) {
        return this.iComboDayReasonClientService.getOne(id);
    }

    @ApiOperation(value = "添加一个事由接口",httpMethod = "POST",nickname = "addOneComboDayReason")
    @PostMapping("/")
    public Result addOne(@RequestBody @NotNull @ApiParam(value = "事由实体",required = true,type = "string") ComboDayReason comboDayReason) {

        return this.iComboDayReasonClientService.addOne(comboDayReason);
    }

    @ApiOperation(value = "更新单个事由接口",httpMethod = "PUT",nickname = "updateOneComboDayReason")
    @PutMapping("/")
    public Result updateBySelective(@RequestBody @NotNull @ApiParam(value = "事由实体",required = true,type = "string") ComboDayReason comboDayReason) {

        return this.iComboDayReasonClientService.updateBySelective(comboDayReason);
    }

    @DeleteMapping("/{id}")
    public Result deleteOne(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") int id) {

        return this.iComboDayReasonClientService.deleteOne(id);

    }


}
