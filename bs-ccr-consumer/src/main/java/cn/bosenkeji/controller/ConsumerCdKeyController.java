package cn.bosenkeji.controller;


import cn.bosenkeji.service.ICdKeyClientService;
import cn.bosenkeji.service.impl.CustomUserDetailsImpl;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKeyOther;
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
    @ApiOperation(value = "生成CdKey",httpMethod = "GET",nickname = "generateCdKeys")
    @GetMapping("/generation")
    public Result generateCdKeys(@RequestParam("num") @Min(1) Integer num, @RequestParam("productComboId") Integer productComboId,
                            @RequestParam("prefix") String prefix, @RequestParam("remark") String remark) {
        return iCdKeyClientService.generateCdKeys(num, productComboId, prefix, remark);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "验证码续费",httpMethod = "POST",nickname = "activation")
    @PostMapping("/activation")
    public Result activation( @RequestParam("key") String key) {
        CustomUserDetailsImpl currentUser = getCurrentUser();
        return iCdKeyClientService.activate(currentUser.getId(), currentUser.getUsername(), key);
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @ApiOperation(value = "验证码续费",httpMethod = "POST",nickname = "renew")
    @PostMapping("/renew")
    public Result renew(@RequestParam("userProductComboId") Integer userProductComboId, @RequestParam("key") String key) {
        CustomUserDetailsImpl currentUser = getCurrentUser();
        return iCdKeyClientService.renew(currentUser.getId(),currentUser.getUsername(),userProductComboId,key);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiOperation(value = "获取验证码分页列表",httpMethod = "GET",nickname = "getCdKeyByPage")
    @GetMapping("/")
    public PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) @Min(1) Integer pageNum, @Min(1) @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return iCdKeyClientService.getCdKeyByPage(pageNum, pageSize);
    }

    private CustomUserDetailsImpl getCurrentUser() {
        CustomUserDetailsImpl principal = (CustomUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal;
    }
}
