package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductComboClientService;
import cn.bosenkeji.vo.ProductCombo;
import feign.hystrix.FallbackFactory;
import org.aspectj.weaver.ArrayAnnotationValue;
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
public class IProductComboClientServiceFallbackFactory implements FallbackFactory<IProductComboClientService> {
    @Override
    public IProductComboClientService create(Throwable throwable) {

        return new IProductComboClientService() {
            @Override
            public ProductCombo getProductCombo(int id) {
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                return productCombo;
            }

            @Override
            public List<ProductCombo> listProductCombo() {
                ProductCombo productCombo=new ProductCombo();
                productCombo.setName("hystrixName");
                List<ProductCombo> list=new ArrayList<>();
                list.add(productCombo);
                return list;
            }

            @Override
            public boolean addProductCombo(ProductCombo productCombo) {
                return false;
            }

            @Override
            public boolean updateProductCombo(ProductCombo productCombo) {
                return false;
            }

            @Override
            public boolean deleteProductCombo(int id) {
                return false;
            }
        };
    }
}
