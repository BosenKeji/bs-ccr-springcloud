package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.service.IProductComboClientService;
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
public class IProdcutComboClientServiceFallbackFactory implements FallbackFactory<IProductComboClientService> {
    @Override
    public IProductComboClientService create(Throwable throwable) {
        return new IProductComboClientService() {
            @Override
            public PageInfo<ProductCombo> listByProductId(int productId) {
                List<ProductCombo> list=new ArrayList<>();
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                list.add(productCombo);
                return new PageInfo<>(list);
            }

            @Override
            public boolean add(ProductCombo productCombo) {
                return false;
            }

            @Override
            public boolean update(ProductCombo productCombo) {
                return false;
            }

            @Override
            public boolean delete(int id) {
                return false;
            }

            @Override
            public Optional<ProductCombo> get(int id) {
                return Optional.empty();
            }

            @Override
            public boolean updateByStatus(int id, int status) {
                return false;
            }
        };
    }
}
