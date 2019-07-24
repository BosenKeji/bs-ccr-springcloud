package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairChoiceAttributeCustomClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttributeCustom;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
            public Result settingParameters(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
                return new Result("0","fail");
            }

            @Override
            public Result updateCoinPairChoiceAttributeCustom(CoinPairChoiceAttributeCustom coinPairChoiceAttributeCustom) {
                return  new Result("0","fail");
            }

            @Override
            public Result deleteOneCoinPairChoiceAttributeCustomByCoinPairChoiceId(int coinPairChoiceId) {
                return  new Result("0","fail");
            }

        };
    }
}
