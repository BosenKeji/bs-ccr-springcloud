package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.*;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class, fallbackFactory = IProductClientServiceFallbackFactory.class)
public interface ICdKeyClientService {

    @PostMapping("/cd_key/generation")
    Result generateCdKeys(@RequestBody GenerateCdKeyParam param);

    @GetMapping("/cd_key/")
    PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum ,
                                        @RequestParam(value = "pageSize",defaultValue = "15",required = false) int pageSize);

    @PostMapping("/cd_key/activation")
    Result activate(@RequestBody ActivateCdKeyUserParam param);

    @PostMapping("/cd_key/renew")
    Result renew(@RequestBody RenewCdKeyUserParam param);

    @GetMapping("/cd_key/search")
    PageInfo<CdKeyOther> getCdKeyBySearch(@RequestParam(value = "cdKey",required = false) String cdKey,
                                          @RequestParam(value = "username",required = false) String username,
                                          @RequestParam(value = "isUsed",required = false) Integer isUsed,
                                          @RequestParam("pageNum") Integer pageNum,
                                          @RequestParam("pageSize") Integer pageSize);
}
