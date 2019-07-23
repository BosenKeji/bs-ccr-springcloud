package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.vo.Coin;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName IProductClientServiceFallbackFactory
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@Component
public class IProdcutClientServiceFallbackFactory implements FallbackFactory<IProductClientService> {
    @Override
    public IProductClientService create(Throwable throwable) {
        return new IProductClientService() {
            @Override
            public Product getProduct(int id) {
                Product product=new Product();
                product.setName("hystrixName");
                return product;
            }

            @Override
            public PageInfo listProduct(int pageNum, int pageSize) {
                Product product = new Product();
                product.setName("hystrixName");
                List<Product> list = new ArrayList<>();
                list.add(product);
                return new PageInfo<>(list);
            }
            @Override
            public Optional<Integer> addProduct(Product product) {
                return Optional.ofNullable(0);
            }

            @Override
            public Optional<Integer> updateProduct(Product product) {
                return Optional.ofNullable(0);
            }

            @Override
            public Optional<Integer> deleteProduct(int id) {
                return Optional.ofNullable(0);
            }

            @Override
            public Optional<Integer> updateProductStatus(int id, int status) {
                return Optional.empty();
            }
        };

    }
}
