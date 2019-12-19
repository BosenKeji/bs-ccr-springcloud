package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceAttributeClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/22 15:27
 */
@Component
public class ICoinPairChoiceAttributeClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoiceAttributeClientService> {
    @Override
    public ICoinPairChoiceAttributeClientService create(Throwable throwable) {
        return new ICoinPairChoiceAttributeClientService() {
            @Override
            public CoinPairChoiceAttribute getCoinPairChoiceAttributeByCoinPartnerChoiceID(int coinPartnerChoiceId) {
                CoinPairChoiceAttribute coinPairChoiceAttribute = new CoinPairChoiceAttribute();
                coinPairChoiceAttribute.setId(0);
                return coinPairChoiceAttribute;
            }

            @Override
            public Result addOneCoinPairChoiceAttribute(String coinPairChoiceIds, int strategyId, double money, int isCustom) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result updateCoinPairChoiceAttribute(CoinPairChoiceAttribute coinPairChoiceAttribute) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result deleteOneCoinPairChoiceAttribute(int coinPartnerChoiceId) {
                return new Result<>(0,"hystrix fail");
            }


        };
    }
}
