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
public class IProductComboClientServiceFallbackFactory implements FallbackFactory<IProductComboClientService> {
    @Override
    public IProductComboClientService create(Throwable throwable) {
        return new IProductComboClientService() {
            @Override
            public PageInfo listByProductId(int productId) {
                List<ProductCombo> list=new ArrayList<>();
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                list.add(productCombo);
                return new PageInfo<>(list);
            }

            @Override
            public PageInfo listByProductIdAndStatus(int pageNum, int pageSize, int productId, int status) {
                List<ProductCombo> list=new ArrayList<>();
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                list.add(productCombo);
                return new PageInfo<>(list);
            }

            @Override
            public Optional<Integer> add(ProductCombo productCombo) {
                return Optional.ofNullable(0);
            }

            @Override
            public Optional<Integer> update(ProductCombo productCombo) {
                return Optional.ofNullable(0);
            }

            @Override
            public Optional<Integer> delete(int id) {
                return Optional.ofNullable(0);
            }

            @Override
            public ProductCombo get(int id) {
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                return productCombo;
            }

            @Override
            public Optional<Integer> updateByStatus(int id, int status) {
               return Optional.empty();
            }
        };
    }
}
