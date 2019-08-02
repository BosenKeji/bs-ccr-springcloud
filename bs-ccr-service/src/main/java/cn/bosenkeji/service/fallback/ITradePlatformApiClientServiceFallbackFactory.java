package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
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
            public TradePlatformApi getOneTradePlatformApiByTradePlatformIdAndUserId(int tradePlatformId, int userId) {
                TradePlatformApi tradePlatformApi = new TradePlatformApi();
                tradePlatformApi.setNickname("hystrix provider-tradePlatform");
                return tradePlatformApi;
            }

            @Override
            public Result addOneTradePlatformApi(TradePlatformApi tradePlatformApi) {
                return  new Result("0","fail");
            }

            @Override
            public Result updateTradePlatformApi(TradePlatformApi tradePlatformApi) {
                return  new Result("0","fail");
            }

            @Override
            public Result deleteOneTradePlatformApi(int tradePlatformId) {
                return  new Result("0","fail");
            }

            @Override
            public TradePlatformApi selectByUserId(Integer userId) {
                return new TradePlatformApi();
            }
        };
    }
}
