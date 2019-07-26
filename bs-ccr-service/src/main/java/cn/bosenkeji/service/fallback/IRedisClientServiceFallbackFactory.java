package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IRedisClientService;
import cn.bosenkeji.util.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @ClassName IProductClientServiceFallbackFactory
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@Component
public class IRedisClientServiceFallbackFactory implements FallbackFactory<IRedisClientService> {
    @Override
    public IRedisClientService create(Throwable throwable) {
        return new IRedisClientService() {

            @Override
            public String get(String key) {
                return "get key fail";
            }

            @Override
            public Result setWithTime(String key, String value, long time) {
                return new Result("hystrix","hystrix");
            }

            @Override
            public Result set(String key, String value) {
                return new Result("hystrix","hystrix");
            }
        };

    }
}
