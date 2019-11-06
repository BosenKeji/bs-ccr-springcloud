package cn.bosenkeji.controller;

import cn.bosenkeji.service.reason.IReasonTypeClientService;
import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/reason_type")
@Api(tags = "reason_type  事由类型接口",value = "提供事由类型相关 Rest API")
@Validated
public class ConsumerReasonTypeController {

    @Resource
    private IReasonTypeClientService iReasonTypeClientService;

    @ApiOperation(value ="获取事由类型列表",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasontTypeListWithPage")
    @GetMapping(value = "/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.iReasonTypeClientService.list(pageNum,pageSize);
    }


    @ApiOperation(value = "获取单个事由类型接口",httpMethod = "GET",nickname = "getOneReasonType")
    @GetMapping("/{id}")
    public ReasonType get(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") @Min(1) int id) {
        return this.iReasonTypeClientService.getOneReasonType(id);
    }
}
