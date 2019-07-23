package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/11 14:50
 */
@Component
public class ICoinSortClientServiceFallbackFactory implements FallbackFactory<ICoinSortClientService> {
    @Override
    public ICoinSortClientService create(Throwable throwable) {
        return new ICoinSortClientService() {
            @Override
            public CoinSort getCoinSort(int id) {
                CoinSort coinSort=new CoinSort();
                return coinSort;
            }

            @Override
            public PageInfo listCoinSort(int pageNum,int pageSize) {
                CoinSort coinSort=new CoinSort();
                List<CoinSort> coinSorts = new ArrayList<>();
                coinSorts.add(coinSort);
                return new PageInfo(coinSorts);
            }

            @Override
            public Optional<Integer> addCoinSort(CoinSort coinSort) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> updateCoinSort(CoinSort coinSort) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> deleteCoinSort(int id) {
                return Optional.of(0);
            }
        };
    }
}
