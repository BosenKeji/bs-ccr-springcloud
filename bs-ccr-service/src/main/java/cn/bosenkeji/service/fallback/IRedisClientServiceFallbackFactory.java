package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.service.IRedisClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
            public Object get(String key) {
                return "get key fail";
            }

            @Override
            public boolean set(String key, String value, long time) {
                return false;
            }
        };

    }
}
