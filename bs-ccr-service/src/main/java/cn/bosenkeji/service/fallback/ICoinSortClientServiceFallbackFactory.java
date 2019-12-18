package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinSortClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageHelper;
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
            public PageInfo listCoinSortByTradePlatformId(int tradePlatformId, int type,int pageNum, int pageSizeCommon) {
                PageHelper.startPage(pageNum,pageSizeCommon);
                List<CoinSort> coinSorts=new ArrayList<>();
                CoinSort coinSort=new CoinSort();
                coinSort.setId(0);
                coinSorts.add(coinSort);
                return new PageInfo<>(coinSorts);
            }

            @Override
            public Result addCoinSort(String tradePlatformName, int coinId, int type) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result updateCoinSort(CoinSort coinSort) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result deleteCoinSort(int tradePlatformId, int coinId) {
                return new Result<>(0,"hystrix fail");
            }
        };
    }
}
