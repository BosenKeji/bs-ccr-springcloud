package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKeyOther;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class, fallbackFactory = IProductClientServiceFallbackFactory.class)
public interface ICdKeyClientService {

    @GetMapping("/cd_key/generation")
    Result generateCdKeys(@RequestParam("num") Integer num, @RequestParam("productComboId") Integer productComboId,
                     @RequestParam("prefix") String prefix, @RequestParam("remark") String remark);

    @GetMapping("/cd_key/")
    PageInfo<CdKeyOther> getCdKeyByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum ,
                                        @RequestParam(value = "pageSize",defaultValue = "15",required = false) int pageSize);

    @PostMapping("/cd_key/activation")
    Result activate(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("key") String key);

    @PostMapping("/cd_key/renew")
    Result renew(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("userProductComboId") Integer userProductComboId, @RequestParam("key") String key);
}
