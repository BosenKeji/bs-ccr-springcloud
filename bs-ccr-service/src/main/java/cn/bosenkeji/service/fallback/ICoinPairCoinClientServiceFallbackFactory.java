package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            public PageInfo listCoinPairCoin(int pageNum,int pageSize) {
                CoinPairCoin coinPairCoin=new CoinPairCoin();
                List<CoinPairCoin> coinPairCoins=new ArrayList<>();
                coinPairCoins.add(coinPairCoin);
                return new PageInfo(coinPairCoins);
            }

            @Override
            public Optional<Integer> addCoinPairCoin(CoinPairCoin coinPairCoin) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> updateCoinPairCoin(CoinPairCoin coinPairCoin) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> deleteCoinPairCoin(int id) {
                return Optional.of(0);
            }
        };
    }
}
