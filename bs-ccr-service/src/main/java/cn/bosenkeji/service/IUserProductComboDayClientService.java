package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserProductComboDayClientServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProductComboDayClientServiceFallbackFactory.class)
public interface IUserProductComboDayClientService {


}
