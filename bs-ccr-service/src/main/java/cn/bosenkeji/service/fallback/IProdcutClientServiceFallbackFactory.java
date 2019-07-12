package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.vo.Coin;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductCombo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ICoinClientServiceFallbackFactory
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
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
            public List<Product> listProduct() {
                Product product = new Product();
                product.setName("hystrixName");
                List<Product> list = new ArrayList<>();
                list.add(product);
                return list;
            }
            @Override
            public boolean addProduct(Product product) {
                return false;
            }

            @Override
            public boolean updateProduct(Product product) {
                return false;
            }

            @Override
            public boolean deleteProduct(int id) {
                return false;
            }
        };

    }
}
