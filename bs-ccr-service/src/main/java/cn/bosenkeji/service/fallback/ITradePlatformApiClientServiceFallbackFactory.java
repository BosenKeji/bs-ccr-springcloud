package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradplateform.TradePlatformApi;
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
            public Result addOneTradePlatformApi(int user, TradePlatformApi tradePlatformApi) {
                return  new Result("0","fail");
            }

            @Override
            public Result updateTradePlatform(TradePlatformApi tradePlatformApi) {
                return  new Result("0","fail");
            }

            @Override
            public Result deleteOneTradePlatform(int tradePlatformId) {
                return  new Result("0","fail");
            }


        };
    }
}
