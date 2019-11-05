package cn.bosenkeji.controller;

import cn.bosenkeji.service.ReasonService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.omg.CORBA.INTERNAL;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/reason")
@Api(tags = "reason 事由相关接口",value="提供事由 相关的 Rest API")
public class ReasonController {

    @Resource
    private ReasonService reasonService;

    @ApiOperation(value ="获取事由列表",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasonListWithPage")
    @GetMapping(value = "/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.reasonService.listWithPage(pageNum,pageSize);
    }

    @ApiOperation(value ="获取事由列表api",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasonByTypeListWithPage")
    @GetMapping(value = "/by_reason_type_id")
    public PageInfo listByReasonTypeId(@RequestParam(value = "reasonTypeId") @ApiParam(value = "事由类别id",required = true,type = "integer",example = "1") int reasonTypeId,
                                       @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                       @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.reasonService.selectByReasonType(reasonTypeId,pageNum,pageSize);
    }

    @ApiOperation(value ="获取 多个事由 列表api",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasonByIdsWithPage")
    @GetMapping(value = "/ids")
    public Map<Integer,Reason> getByIds(@RequestParam(value = "ids") @ApiParam(value = "事由类别ids",required = true,type = "set",example = "1") Set<Integer> ids)
    {
        return this.reasonService.selectByPrimaryKeys(ids);
    }

    @ApiOperation(value ="根据id 检查是否存在 reason",httpMethod = "GET",nickname = "checkExistById")
    @GetMapping(value = "/exist_by_id")
    public Result<Integer> checkExistById(@RequestParam(value = "id") @ApiParam(value = "事由id",required = true,type = "integer",example = "1") int id)
    {
        return new Result<Integer>(this.reasonService.checkExistById(id));
    }

    @ApiOperation(value = "获取单个事由接口",httpMethod = "GET",nickname = "getOneReason")
    @GetMapping("/{id}")
    public Reason get(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") int id) {
        return this.reasonService.get(id);
    }
}
