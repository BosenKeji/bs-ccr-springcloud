package cn.bosenkeji.controller;


import cn.bosenkeji.service.ICdKeyClientService;
import cn.bosenkeji.service.impl.CustomUserDetailsImpl;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.*;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/cd_key")
@Validated
@Api(tags = "CdKey 激活码相关接口", value = "提供激活码相关接口的 Rest API")
public class ConsumerCdKeyController {

    @Autowired
    private ICdKeyClientService iCdKeyClientService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "生成CdKey",httpMethod = "POST",nickname = "generateCdKeys")
    @PostMapping("/generation")
    public Result generateCdKeys(@RequestBody GenerateCdKeyParam generateCdKeyParam) {
        return iCdKeyClientService.generateCdKeys(generateCdKeyParam);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "验证码激活",httpMethod = "POST",nickname = "activation")
    @PostMapping("/activation")
    public Result activation( @RequestParam("key") String key) {
        CustomUserDetailsImpl currentUser = getCurrentUser();
        ActivateCdKeyUserParam param = new ActivateCdKeyUserParam(currentUser.getId(),key);
        return iCdKeyClientService.activate(param);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "验证码续费",httpMethod = "POST",nickname = "renew")
    @PostMapping("/renew")
    public Result renew(@RequestBody RenewCdKeyParam param) {
        CustomUserDetailsImpl currentUser = getCurrentUser();
        RenewCdKeyUserParam renewCdKeyUserParam = new RenewCdKeyUserParam(currentUser.getId(),param.getUserProductComboId(),param.getCdKey());
        return iCdKeyClientService.renew(renewCdKeyUserParam);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "获取验证码分页列表",httpMethod = "GET",nickname = "getCdKeyByPage")
    @GetMapping("/")
    public PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) @Min(1) Integer pageNum, @Min(1) @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return iCdKeyClientService.getCdKeyByPage(pageNum, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "按照条件，获取验证码分页列表",httpMethod = "GET",nickname = "getCdKeyBySearch")
    @GetMapping("/search")
    public PageInfo<CdKeyOther> getCdKeyBySearch(@RequestParam(value = "cdKey",required = false) String cdKey,
                                                    @RequestParam(value = "username",required = false) String username,
                                                    @RequestParam(value = "isUsed",required = false) Integer isUsed,
                                                    @RequestParam(value = "userProductComboId",required = false) Integer userProductComboId,
                                                    @RequestParam(value = "sort",required = false,defaultValue = "0") Integer sort,
                                                    @RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize) {
        return iCdKeyClientService.getCdKeyBySearch(cdKey,username,isUsed,userProductComboId, sort,pageNum,pageSize);
    }

    private CustomUserDetailsImpl getCurrentUser() {
        CustomUserDetailsImpl principal = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}
