package cn.bosenkeji.service.reason;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.reason.IReasonClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.reason.Reason;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IReasonClientServiceFallbackFactory.class)
public interface IReasonClientService {

    String PRE_URL="/reason/";


    @GetMapping(PRE_URL+"{id}")
    Reason getOneReason(@PathVariable("id") int id);

    @GetMapping(PRE_URL+"ids")
    Map<Integer,Reason> getSomeReason(@RequestParam("ids") Set<Integer> ids);

    @GetMapping(PRE_URL)
    PageInfo list(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize);

    @GetMapping(PRE_URL+"by_reason_type_id")
    PageInfo listByReasonType(@RequestParam("reasonTypeId") int reasonTypeId,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize);

    @GetMapping(PRE_URL+"exist_by_id")
    Result<Integer> checkExistById(@RequestParam("id") int id);

}
