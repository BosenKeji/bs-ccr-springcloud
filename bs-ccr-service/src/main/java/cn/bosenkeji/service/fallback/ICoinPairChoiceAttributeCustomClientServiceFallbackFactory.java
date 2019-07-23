package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceAttributeCustomClientService;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author CAJR
 * @create 2019/7/22 15:26
 */
@Component
public class ICoinPairChoiceAttributeCustomClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoiceAttributeCustomClientService> {
    @Override
    public ICoinPairChoiceAttributeCustomClientService create(Throwable throwable) {
        return new ICoinPairChoiceAttributeCustomClientService() {
            @Override
            public CoinPairChoiceAttributeCustom getCoinPairChoiceAttributeCustomByCoinPairChoiceId(int coinPairChoiceId) {
                CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom = new CoinPairChoiceAttributeCustom();
                coinPairChoiceAttributeCustom.setId(0);
                return coinPairChoiceAttributeCustom;
            }

            @Override
            public boolean settingParameters(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoiceAttributeCustom(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoiceAttributeCustomByCoinPairChoiceId(int coinPairChoiceId) {
                return false;
            }
        };
    }
}
