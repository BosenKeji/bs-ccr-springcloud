package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.vo.CoinSort;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
            public List<CoinSort> listCoinSort() {
                CoinSort coinSort=new CoinSort();
                List<CoinSort> coinSorts = new ArrayList<>();
                coinSorts.add(coinSort);
                return coinSorts;
            }

            @Override
            public boolean addCoinSort(CoinSort coinSort) {
                return false;
            }

            @Override
            public boolean updateCoinSort(CoinSort coinSort) {
                return false;
            }

            @Override
            public boolean deleteCoinSort(int id) {
                return false;
            }
        };
    }
}
