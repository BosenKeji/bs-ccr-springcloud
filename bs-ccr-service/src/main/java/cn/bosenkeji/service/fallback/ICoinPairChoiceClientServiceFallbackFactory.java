package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceClientService;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
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
public class ICoinPairChoiceClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoiceClientService> {
    @Override
    public ICoinPairChoiceClientService create(Throwable throwable) {
        return new ICoinPairChoiceClientService() {
            @Override
            public PageInfo getListCoinPairChoiceWithPage(int pageNum, int pageSizeCommon) {
                List<CoinPairChoice> coinPairChoices = new ArrayList<>();
                CoinPairChoice coinPairChoice = new CoinPairChoice();
                coinPairChoice.setId(0);
                coinPairChoices.add(coinPairChoice);
                return new PageInfo(coinPairChoices);
            }

            @Override
            public CoinPairChoice getOneCoinPairChoice(int id) {
                CoinPairChoice coinPairChoice = new CoinPairChoice();
                coinPairChoice.setId(0);
                return coinPairChoice;
            }

            @Override
            public boolean addOneCoinPairChoice(int userId, int strategyStatus, int coinPairId) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoice(CoinPairChoice coinPairChoice) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoice(int id) {
                return false;
            }
        };
    }
}
