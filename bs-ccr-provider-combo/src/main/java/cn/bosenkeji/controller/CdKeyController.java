package cn.bosenkeji.controller;

import cn.bosenkeji.service.CdKeyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.ExcelUtil;
import cn.bosenkeji.vo.cdKey.CdKey;
import cn.bosenkeji.vo.cdKey.CdKeyOther;
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


    @GetMapping("/generation")
    public Result getCdKeys(@RequestParam("num") Integer num, @RequestParam("productComboId") Integer productComboId,
                            @RequestParam("prefix") String prefix, @RequestParam("remark") String remark) {
        return cdKeyService.generateCdKeys(num, productComboId, prefix, remark);
    }

    @PostMapping("/activation")
    public Result activation(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("key") String key) {
        return cdKeyService.activate(userId, username, key);
    }

    @PostMapping("/renew")
    public Result renew(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("userProductComboId") Integer userProductComboId, @RequestParam("key") String key) {
        return cdKeyService.renew(userId,username,userProductComboId,key);
    }

    @GetMapping("/")
    public PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return cdKeyService.getCdKeyByPage(pageNum, pageSize);
    }

}