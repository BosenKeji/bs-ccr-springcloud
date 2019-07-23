package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceClientService;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            public Optional<Integer> addOneCoinPairChoice(int userId, int strategyStatus, int coinPairId) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> updateCoinPairChoice(CoinPairChoice coinPairChoice) {
                return Optional.of(0);
            }

            @Override
            public Optional<Integer> deleteOneCoinPairChoice(int id) {
                return Optional.of(0);
            }
        };
    }
}