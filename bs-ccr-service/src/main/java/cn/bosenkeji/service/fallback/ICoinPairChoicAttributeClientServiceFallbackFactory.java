package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoicAttributeClientService;
import cn.bosenkeji.vo.CoinPairChoicAttribute;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author CAJR
 * @create 2019/7/22 15:27
 */
@Component
public class ICoinPairChoicAttributeClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoicAttributeClientService> {
    @Override
    public ICoinPairChoicAttributeClientService create(Throwable throwable) {
        return new ICoinPairChoicAttributeClientService() {
            @Override
            public CoinPairChoicAttribute getCoinPairChoicAttributeByCoinPartnerChoicID(int coinPartnerChoicId) {
                CoinPairChoicAttribute coinPairChoicAttribute = new CoinPairChoicAttribute();
                coinPairChoicAttribute.setId(0);
                return coinPairChoicAttribute;
            }

            @Override
            public boolean addOneCoinPairChoicAttribute(int[] coinPairChoicIds, int lever, int money, int isCustom) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoicAttribute(CoinPairChoicAttribute coinPairChoicAttribute) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoicAttribute(int coinPartnerChoicId) {
                return false;
            }
        };
    }
}
