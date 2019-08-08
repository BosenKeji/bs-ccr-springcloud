package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            public CoinPair getCoinPairByName(String name) {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                return coinPair;
            }

            @Override
            public PageInfo listCoinPair(int pageNum,int pageSize) {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                List<CoinPair> coinPairs=new ArrayList<>();
                coinPairs.add(coinPair);
                return new PageInfo(coinPairs);
            }

            @Override
            public Result addCoinPair(CoinPair coinPair) {
                return  new Result("0","fail");
            }

            @Override
            public Result updateCoinPair(CoinPair coinPair) {
                return  new Result("0","fail");
            }

            @Override
            public Result deleteCoinPair(int id) {
                return  new Result("0","fail");
            }

            @Override
            public List<CoinPair> findAll() {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                List<CoinPair> coinPairs=new ArrayList<>();
                coinPairs.add(coinPair);
                return coinPairs;
            }

            @Override
            public List<CoinPair> findSection(List<Integer> ids) {
                CoinPair coinPair=new CoinPair();
                coinPair.setName("hystrix provider-coin");
                List<CoinPair> coinPairs=new ArrayList<>();
                coinPairs.add(coinPair);
                return coinPairs;
            }

        };
    }
}
