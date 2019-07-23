package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformClientService;
import cn.bosenkeji.vo.tradeplateform.TradePlatform;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/22 10:44
 */
@Component
public class ITradePlatformClientServiceFallbackFactory implements FallbackFactory<ITradePlatformClientService>{
    @Override
    public ITradePlatformClientService create(Throwable throwable) {
        return new ITradePlatformClientService() {
            @Override
            public PageInfo listTradePlatformWithPage(int pageNum, int pageSizeCommon) {
                List<TradePlatform> tradePlatforms=new ArrayList<>();
                TradePlatform tradePlatform=new TradePlatform();
                tradePlatform.setName("hystrix provider-tradePlatform");
                tradePlatforms.add(tradePlatform);
                return new PageInfo(tradePlatforms);
            }

            @Override
            public TradePlatform getOneTrdPatform(int id) {
                TradePlatform tradePlatform=new TradePlatform();
                tradePlatform.setName("hystrix provider-tradePlatform");
                return tradePlatform;
            }

            @Override
            public boolean addOneTradePlatform(TradePlatform tradePlatform) {
                return false;
            }

            @Override
            public boolean updateTradePlatform(TradePlatform tradePlatform) {
                return false;
            }

            @Override
            public boolean deleteOneTradePlatform(int id) {
                return false;
            }
        };
    }
}
