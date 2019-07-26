package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplateform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-26 18:16
 */
@Component
public class ITradePlatformApiBindProductComboClientServiceFallbackFactory implements FallbackFactory<ITradePlatformApiBindProductComboClientService> {

    @Override
    public ITradePlatformApiBindProductComboClientService create(Throwable throwable) {
        return new ITradePlatformApiBindProductComboClientService() {
            @Override
            public TradePlatformApiBindProductCombo getTradePlatformApiBindProductCombo(int userId) {
                return null;
            }

            @Override
            public Result addTradePlatformApiBindProductCombo(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
                return null;
            }
        };
    }
}
