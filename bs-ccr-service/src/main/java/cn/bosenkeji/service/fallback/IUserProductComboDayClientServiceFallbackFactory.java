package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.IUserProductComboDayClientService;
import cn.bosenkeji.vo.ProductCombo;
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
 * @create 2019-07-18 11:30
 */
@Component
public class IUserProductComboDayClientServiceFallbackFactory implements FallbackFactory<IUserProductComboDayClientService> {
    @Override
    public IUserProductComboDayClientService create(Throwable throwable) {
        return new IUserProductComboDayClientService() {

        };
    }
}
