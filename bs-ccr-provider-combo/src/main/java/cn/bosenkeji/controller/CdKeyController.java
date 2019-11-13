package cn.bosenkeji.controller;

import cn.bosenkeji.service.CdKeyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.ExcelUtil;
import cn.bosenkeji.vo.cdKey.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cd_key")
@Api(tags = "cdKey 激活码相关接口",value = "提供cdKey相关的 Rest Api")
@Validated
public class CdKeyController {

    @Autowired
    private CdKeyService cdKeyService;


    @PostMapping("/generation")
    public Result getCdKeys(@RequestBody GenerateCdKeyParam param) {
        return cdKeyService.generateCdKeys(param);
    }

    @PostMapping("/activation")
    public Result activation(@RequestBody ActivateCdKeyUserParam param) {
        return cdKeyService.activate(param);
    }

    @PostMapping("/renew")
    public Result renew(@RequestBody RenewCdKeyUserParam param) {
        return cdKeyService.renew(param);
    }

    @GetMapping("/")
    public PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return cdKeyService.getCdKeyByPage(pageNum, pageSize);
    }

    @GetMapping("/search")
    public PageInfo<CdKeyOther> getCdKeyBySelective(@RequestParam(value = "cdKey",required = false) String cdKey,
                                                    @RequestParam(value = "username",required = false) String username,
                                                    @RequestParam(value = "isUsed",required = false) Integer isUsed,
                                                    @RequestParam("pageNum") Integer pageNum,
                                                    @RequestParam("pageSize") Integer pageSize) {
        return cdKeyService.getCdKeyBySearch(cdKey,username,isUsed,pageNum,pageSize);
    }

}