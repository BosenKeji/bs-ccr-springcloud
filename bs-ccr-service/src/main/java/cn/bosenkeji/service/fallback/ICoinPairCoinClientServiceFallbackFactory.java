package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairCoinClientService;
import cn.bosenkeji.util.Result;
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
            public PageInfo listByPage(int pageNum, int pageSizeCommon) {
                List<CoinPairCoin> coinPairCoins = new ArrayList<>();
                CoinPairCoin coinPairCoin = new CoinPairCoin();
                coinPairCoin.setCoinId(0);
                coinPairCoins.add(coinPairCoin);
                return new PageInfo<>(coinPairCoins);
            }

            @Override
            public List<CoinPairCoin> listByCoinId(int coinId) {
                List<CoinPairCoin> coinPairCoins = new ArrayList<>();
                CoinPairCoin coinPairCoin = new CoinPairCoin();
                coinPairCoin.setCoinId(0);
                coinPairCoins.add(coinPairCoin);
                return coinPairCoins;
            }

            @Override
            public Result addCoinPairCoin(CoinPairCoin coinPairCoin) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result updateCoinPairCoin(CoinPairCoin coinPairCoin) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result deleteCoinPairCoin(int coinId, int coinPairId) {
                return new Result<>(0,"hystrix fail");
            }


        };
    }
}
