package cn.bosenkeji.service.reason;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.reason.IReasonTypeClientServiceFallbackFactory;
import cn.bosenkeji.vo.reason.ReasonType;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IReasonTypeClientServiceFallbackFactory.class)
public interface IReasonTypeClientService {

    String PRE_URL="/reason_type/";

    @GetMapping(PRE_URL+"{id}")
    ReasonType getOneReasonType(@PathVariable("id") int id);

    @GetMapping(PRE_URL)
    PageInfo list(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize);


}
