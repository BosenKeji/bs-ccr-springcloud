package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoicAttributeCustomClientService;
import cn.bosenkeji.vo.CoinPairChoicAttributeCustom;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Author CAJR
 * @create 2019/7/22 15:26
 */
@Component
public class ICoinPairChoicAttributeCustomClientServiceFallbackFactory implements FallbackFactory<ICoinPairChoicAttributeCustomClientService> {
    @Override
    public ICoinPairChoicAttributeCustomClientService create(Throwable throwable) {
        return new ICoinPairChoicAttributeCustomClientService() {
            @Override
            public CoinPairChoicAttributeCustom getCoinPairChoicAttributeCustomByCoinPairChoicId(int coinPairChoicId) {
                CoinPairChoicAttributeCustom coinPairChoicAttributeCustom = new CoinPairChoicAttributeCustom();
                coinPairChoicAttributeCustom.setId(0);
                return coinPairChoicAttributeCustom;
            }

            @Override
            public boolean settingParameters(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom) {
                return false;
            }

            @Override
            public boolean updateCoinPairChoicAttributeCustom(CoinPairChoicAttributeCustom coinPairChoicAttributeCustom) {
                return false;
            }

            @Override
            public boolean deleteOneCoinPairChoicAttributeCustomByCoinPairChoicId(int coinPairChoicId) {
                return false;
            }
        };
    }
}
