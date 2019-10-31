package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProductClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.cdKey.CdKey;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class)
public interface ICdKeyClientService {

    @GetMapping("/cd_key/")
    Result getCdKeys(@RequestParam("num") Integer num, @RequestParam("productComboId") Integer productComboId,
                     @RequestParam("prefix") String prefix, @RequestParam("remark") String remark);

    @GetMapping("/cd_key/list")
    PageInfo<CdKey> list(@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum ,
                         @RequestParam(value = "pageSize",defaultValue = "15",required = false) int pageSize);

    @PostMapping("/cd_key/activate")
    Result activate(@RequestParam("userId") Integer userId, @RequestParam("username") String username, @RequestParam("key") String key);

    @PostMapping("/cd_key/renew")
    Result renew(Integer userId, String username, Integer userProductComboId, String key);
}
