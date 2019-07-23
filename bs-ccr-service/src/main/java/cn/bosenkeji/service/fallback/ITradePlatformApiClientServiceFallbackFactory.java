package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApi;
import cn.bosenkeji.vo.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
            public Optional<Integer> addOneTradePlatformApi(int userId,TradePlatformApi tradePlatformApi) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> updateTradePlatform(TradePlatformApi tradePlatformApi) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> deleteOneTradePlatform(int tradePlatformId) {
                return Optional.of(0);
            }
        };
    }
}
