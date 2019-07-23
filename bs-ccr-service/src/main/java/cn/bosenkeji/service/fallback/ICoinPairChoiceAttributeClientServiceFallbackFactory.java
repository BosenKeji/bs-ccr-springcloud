package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceAttributeClientService;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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
            public boolean addOneCoinPairChoiceAttribute(int[] coinPairChoiceIds, int lever, int money, int isCustom) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoiceAttribute(CoinPairChoiceAttribute coinPairChoiceAttribute) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoiceAttribute(int coinPartnerChoiceId) {
                return false;
            }
        };
    }
}
