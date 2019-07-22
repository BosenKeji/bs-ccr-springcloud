package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.vo.TradePlatformApi;
import cn.bosenkeji.vo.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author CAJR
 * @create 2019/7/22 10:55
 */
@Component
public class ITradePlatformApiClientServiceFallbackFactory implements FallbackFactory<ITradePlatformApiClientService> {
    @Override
    public ITradePlatformApiClientService create(Throwable throwable) {
        return new ITradePlatformApiClientService() {
            @Override
            public TradePlatformApi getOneTradePlatformApi(int id) {
                TradePlatformApi tradePlatformApi = new TradePlatformApi();
                tradePlatformApi.setNickname("hystrix provider-tradePlatform");
                return tradePlatformApi;
            }

            @Override
            public boolean addOneTradePlatformApi(User user,TradePlatformApi tradePlatformApi) {
                return false;
            }

            @Override
            public boolean updateTradePlatform(TradePlatformApi tradePlatformApi) {
                return false;
            }

            @Override
            public boolean deleteOneTradePlatform(int tradePlatformId) {
                return false;
            }
        };
    }
}
