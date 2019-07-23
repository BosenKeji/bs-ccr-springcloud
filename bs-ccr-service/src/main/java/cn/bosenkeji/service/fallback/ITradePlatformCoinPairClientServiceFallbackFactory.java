package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformCoinPairClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
            public boolean addOneTradePlatformCoinPair(TradePlatformCoinPair tradePlatformCoinPair) {
                return false;
            }

            @Override
            public boolean updateTradePlatformCoinPair(TradePlatformCoinPair tradePlatformCoinPair) {
                return false;
            }

            @Override
            public boolean deleteOneTradePlatformCoinPair(int tradePlatformId,int coinPairId) {
                return false;
            }
        };
    }
}
