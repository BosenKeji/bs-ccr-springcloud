/**
 * FileName: IComboDayReasonclientService
 * Author: xivin
 * Date: 2019-09-23 10:55
 * Description:
 */
package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IComboDayReasonClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.ComboDayReason;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IComboDayReasonClientServiceFallbackFactory.class)
public interface IComboDayReasonClientService {

    final String Pre_Mapping="/combo_day_reason";

    @GetMapping(Pre_Mapping+"/")
    PageInfo listByPage(@RequestParam(value="pageNum",defaultValue="1") int pageNum,
                               @RequestParam(value="pageSize",defaultValue="15") int pageSize);

    @GetMapping(Pre_Mapping+"/{id}")
    ComboDayReason getOne(@PathVariable("id") int id);

    @PostMapping(Pre_Mapping+"/")
    Result addOne(@RequestBody ComboDayReason comboDayReason);

    @PutMapping(Pre_Mapping+"/")
    Result updateBySelective(@RequestBody ComboDayReason comboDayReason);

    @DeleteMapping(Pre_Mapping+"/{id}")
    Result deleteOne(@PathVariable("id") int id);
}
