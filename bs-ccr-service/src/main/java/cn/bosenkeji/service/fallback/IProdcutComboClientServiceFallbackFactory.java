package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-18 11:30
 */
//@Component
public class IProdcutComboClientServiceFallbackFactory implements FallbackFactory<IProductComboClientService> {
    @Override
    public IProductComboClientService create(Throwable throwable) {
        return new IProductComboClientService() {

            @Override
            public PageInfo<ProductCombo> listByProductId(int productId) {
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                List<ProductCombo> list=new ArrayList<>();
                list.add(productCombo);
                return new PageInfo<>(list);
            }
        };
    }
}
