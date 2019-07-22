package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoicClientService;
import cn.bosenkeji.vo.CoinPair;
import cn.bosenkeji.vo.CoinPairChoic;
import cn.bosenkeji.vo.Strategy;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/22 15:26
 */
@Component
public class ICoinPairChoicClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoicClientService> {
    @Override
    public ICoinPairChoicClientService create(Throwable throwable) {
        return new ICoinPairChoicClientService() {
            @Override
            public PageInfo getListCoinPairChoicWithPage(int pageNum, int pageSizeCommon) {
                List<CoinPairChoic> coinPairChoics = new ArrayList<>();
                CoinPairChoic coinPairChoic = new CoinPairChoic();
                coinPairChoic.setId(0);
                coinPairChoics.add(coinPairChoic);
                return new PageInfo(coinPairChoics);
            }

            @Override
            public CoinPairChoic getOneCoinPairChoic(int id) {
                CoinPairChoic coinPairChoic = new CoinPairChoic();
                coinPairChoic.setId(0);
                return coinPairChoic;
            }

            @Override
            public boolean addOneCoinPairChoic(int userId, int strategyStatus, int coinPairId) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoic(CoinPairChoic coinPairChoic) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoic(int id) {
                return false;
            }
        };
    }
}
