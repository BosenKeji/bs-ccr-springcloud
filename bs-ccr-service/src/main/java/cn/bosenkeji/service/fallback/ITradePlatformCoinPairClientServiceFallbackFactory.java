package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformCoinPairClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 11:00
 */
@Component
public class ITradePlatformCoinPairClientServiceFallbackFactory implements FallbackFactory<ITradePlatformCoinPairClientService> {
    @Override
    public ITradePlatformCoinPairClientService create(Throwable throwable) {
        return new ITradePlatformCoinPairClientService() {
            @Override
            public PageInfo listTradePlatformCoinPairs(int pageNum, int pageSizeCommon) {
                List<TradePlatformCoinPair> tradePlatformCoinPairs = new ArrayList<>();
                TradePlatformCoinPair tradePlatformCoinPair = new TradePlatformCoinPair();
                tradePlatformCoinPair.setId(0);
                tradePlatformCoinPairs.add(tradePlatformCoinPair);
                return new PageInfo(tradePlatformCoinPairs);
            }

            @Override
            public TradePlatformCoinPair getOneTradePlatformCoinPair(int id) {
                TradePlatformCoinPair tradePlatformCoinPair = new TradePlatformCoinPair();
                tradePlatformCoinPair.setId(0);
                return tradePlatformCoinPair;
            }

            @Override
            public Optional<Integer> addOneTradePlatformCoinPair(TradePlatformCoinPair tradePlatformCoinPair) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> updateTradePlatformCoinPair(TradePlatformCoinPair tradePlatformCoinPair) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> deleteOneTradePlatformCoinPair(int tradePlatformId, int coinPairId) {
                return Optional.of(0);
            }
        };
    }
}
