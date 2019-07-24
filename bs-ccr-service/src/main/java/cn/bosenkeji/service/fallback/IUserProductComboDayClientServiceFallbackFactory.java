package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserProductComboDayClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-18 11:30
 */
@Component
public class IUserProductComboDayClientServiceFallbackFactory implements FallbackFactory<IUserProductComboDayClientService> {
    @Override
    public IUserProductComboDayClientService create(Throwable throwable) {
        return new IUserProductComboDayClientService() {

        };
    }
}
