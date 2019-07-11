package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.vo.CoinPair;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 12:03
 */
@Component
public class ICoinPairClientServiceFallbackFactory implements FallbackFactory<ICoinPairClientService> {

    @Override
    public ICoinPairClientService create(Throwable throwable) {

        return new ICoinPairClientService() {
            @Override
            public CoinPair getCoinPair(int id) {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                return coinPair;
            }

            @Override
            public List<CoinPair> listCoinPair() {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                List<CoinPair> coinPairs=new ArrayList<>();
                coinPairs.add(coinPair);
                return coinPairs;
            }

            @Override
            public boolean addCoinPair(CoinPair coinPair) {
                return false;
            }

            @Override
            public boolean updateCoinPair(CoinPair coinPair) {
                return false;
            }

            @Override
            public boolean deleteCoinPair(int id) {
                return false;
            }
        };
    }
}
