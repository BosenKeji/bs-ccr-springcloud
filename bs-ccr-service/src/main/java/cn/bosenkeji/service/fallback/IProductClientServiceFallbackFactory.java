package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IProductClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName IProductClientServiceFallbackFactory
 * @Description TODO
 * @Author Xivin
 * @Versio V1.0
 **/
@Component
public class IProductClientServiceFallbackFactory implements FallbackFactory<IProductClientService> {
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
                return new PageInfo(list);
            }

            @Override
            public Result updateProductStatus(int id, int status) {
                return new Result("0","hystrix");
            }

            @Override
            public Result addProduct(Product product) {
                return new Result("0","hystrix");
            }

            @Override
            public Result updateProduct(Product product) {
                return new Result("0","hystrix");
            }

            @Override
            public Result deleteProduct(int id) {
                return new Result("0","hystrix");
            }

            @Override
            public Map<Integer, Product> listByPrimaryKeys(List<Integer> ids) {
                Map<Integer,Product> map=new HashMap<>();
                Product product=new Product();
                product.setId(0);
                product.setName("hystrix");
                product.setVersionName("hystrix");
                map.put(product.getId(),product);
                return map;
            }
        };

    }
}
