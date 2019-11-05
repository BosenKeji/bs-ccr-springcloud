package cn.bosenkeji.controller;

import cn.bosenkeji.service.ReasonTypeService;
import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

@RequestMapping("/reason_type")
@RestController
@Api(tags = "reason 事由类型 相关接口",value="提供事由类型 相关的 Rest API")
public class ReasonTypeController {

    @Resource
    private ReasonTypeService reasonTypeService;

    @ApiOperation(value ="获取事由类型列表",notes = "获取事由列表api",httpMethod = "GET",nickname = "getReasontTypeListWithPage")
    @GetMapping(value = "/")
    public PageInfo list(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                         @RequestParam(value="pageSize",defaultValue="10") int pageSize)
    {
        return this.reasonTypeService.listWithPage(pageNum,pageSize);
    }


    @ApiOperation(value = "获取单个事由类型接口",httpMethod = "GET",nickname = "getOneReasonType")
    @GetMapping("/{id}")
    public ReasonType get(@PathVariable("id") @Min(1) @ApiParam(value = "事由ID",required = true,type = "integer",example = "1") int id) {
        return this.reasonTypeService.get(id);
    }
}
