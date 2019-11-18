package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoice;
import cn.bosenkeji.vo.transaction.CoinPairChoiceJoinCoinPair;
import cn.bosenkeji.vo.transaction.CoinPairChoiceShellOrBuyResult;
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
            public PageInfo getListCoinPairChoiceWithPage(int pageNum, int pageSizeCommon,int tradePlatformApiBindProductComboId,int coinId) {
                List<CoinPairChoice> coinPairChoices = new ArrayList<>();
                CoinPairChoice coinPairChoice = new CoinPairChoice();
                coinPairChoice.setId(0);
                coinPairChoices.add(coinPairChoice);
                return new PageInfo<>(coinPairChoices);
            }

            @Override
            public Result checkExistByCoinPairNameAndTradePlatformApiBindProductComboId(String coinPairName, int tradePlatformApiBindProductComboId) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public CoinPairChoice getOneCoinPairChoice(int id) {
                CoinPairChoice coinPairChoice = new CoinPairChoice();
                coinPairChoice.setId(0);
                return coinPairChoice;
            }

            @Override
            public Result addOneCoinPairChoice(int tradePlatformApiBindProductComboId, int strategyStatus, int coinPairId) {
                return  new Result<>(0,"hystrix fail");
            }

            @Override
            public Result updateCoinPairChoice(CoinPairChoice coinPairChoice) {
                return  new Result<>(0,"hystrix fail");
            }

            @Override
            public Result deleteOneCoinPairChoice(int id,int tradePlatformApiBindProductComboId) {
                return  new Result<>(0,"fail");
            }

            @Override
            public List<CoinPairChoice> findAll() {
                List list = new ArrayList();
                CoinPairChoice coinPairChoice = new CoinPairChoice();
                list.add(coinPairChoice);
                return list;
            }

            @Override
            public Result batchDelete(String coinPairChoiceIds,int tradePlatformApiBindProductComboId) {
                return  new Result<>(0,"hystrix fail");
            }

            @Override
            public PageInfo recordByCoinId(int pageNum, int pageSizeCommon, int tradePlatformApiBindProductComboId, int coinId, String type) {
                List<CoinPairChoiceShellOrBuyResult> coinPairChoices = new ArrayList<>();
                CoinPairChoiceShellOrBuyResult coinPairChoice = new CoinPairChoiceShellOrBuyResult();
                coinPairChoice.setCoinPairChoiceId(0);
                coinPairChoices.add(coinPairChoice);
                return new PageInfo<>(coinPairChoices);
            }

            @Override
            public Result getCoinPairChoicePositionDetails(int coinPairChoiceId) {
                return new Result<>(0,"hystrix fail");
            }
        };
    }
}
