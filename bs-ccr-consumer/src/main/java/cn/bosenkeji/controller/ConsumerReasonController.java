package cn.bosenkeji.controller;

import cn.bosenkeji.service.reason.IReasonClientService;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/reason")
@Api(tags = "reason  事由接口",value = "提供事由相关 Rest API")
@Validated
public class ConsumerReasonController {

    @Resource
    private IReasonClientService iReasonClientService;

    @ApiOperation(value ="获取事由列表",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasonListWithPage")
    @GetMapping(value = "/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.iReasonClientService.list(pageNum,pageSize);
    }

    @ApiOperation(value ="获取事由列表api",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasonByTypeListWithPage")
    @GetMapping(value = "/by_reason_type_id")
    public PageInfo listByReasonTypeId(@RequestParam(value = "reasonTypeId") @ApiParam(value = "事由类别id",required = true,type = "integer",example = "1") @Min(1) int reasonTypeId,
                                       @RequestParam(value="pageNum",defaultValue="1") int pageNum,
                                       @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.iReasonClientService.listByReasonType(reasonTypeId,pageNum,pageSize);
    }

    @ApiOperation(value = "获取单个事由接口",httpMethod = "GET",nickname = "getOneReason")
    @GetMapping("/{id}")
    public Reason get(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") @Min(1) int id) {
        return this.iReasonClientService.getOneReason(id);
    }

}
