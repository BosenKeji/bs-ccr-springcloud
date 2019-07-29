package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-26 19:48
 */
@Component
public class ITradePlatformApiBindProductComboClientServiceFallbackFactory implements FallbackFactory<ITradePlatformApiBindProductComboClientService> {

    @Override
    public ITradePlatformApiBindProductComboClientService create(Throwable throwable) {
        return new ITradePlatformApiBindProductComboClientService() {
            @Override
            public PageInfo getListByUserId(int pageNum,int pageSize,int userId) {
                TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo=new TradePlatformApiBindProductCombo();
                List list=new ArrayList();
                list.add(tradePlatformApiBindProductCombo);
                return new PageInfo(list);
            }

            @Override
            public Result addTradePlatformApiBindProductCombo(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
                return new Result("0","hystrix");
            }

            @Override
            public PageInfo getNoBindTradePlatformApiListByUserId(int pageNum, int pageSize, int userId) {
                return new PageInfo();
            }

            @Override
            public PageInfo getNoBindUserProductComboListByUserId(int pageNum, int pageSize, int userId) {
                return new PageInfo();
            }

            @Override
            public Result updateTradePlatformApiBindProductCombo(int id, int tradePlatformApiId, int userId) {
                return new Result("0","fail");
            }

            @Override
            public Result deleteTradePlatformApiBindProductCombo(int id, int userId) {
                return new Result("0","fail");
            }
        };
    }
}
