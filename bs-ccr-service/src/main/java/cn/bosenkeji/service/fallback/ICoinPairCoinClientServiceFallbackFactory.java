package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.CoinPairCoin;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/11 14:32
 */
@Component
public class ICoinPairCoinClientServiceFallbackFactory implements FallbackFactory<ICoinPairCoinClientService> {
    @Override
    public ICoinPairCoinClientService create(Throwable throwable) {
        return new ICoinPairCoinClientService() {
            @Override
            public CoinPairCoin getCoinPairCoin(int id) {
                CoinPairCoin coinPairCoin=new CoinPairCoin();
                return coinPairCoin;
            }

            @Override
            public List<CoinPairCoin> listCoinPairCoin() {
                CoinPairCoin coinPairCoin=new CoinPairCoin();
                List<CoinPairCoin> coinPairCoins=new ArrayList<>();
                coinPairCoins.add(coinPairCoin);
                return coinPairCoins;
            }

            @Override
            public boolean addCoinPairCoin(CoinPairCoin coinPairCoin) {
                return false;
            }

            @Override
            public boolean updateCoinPairCoin(CoinPairCoin coinPairCoin) {
                return false;
            }

            @Override
            public boolean deleteCoinPairCoin(int id) {
                return false;
            }
        };
    }
}
