package cn.bosenkeji.service;

import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:37
 */
public interface IProductService {

    PageInfo<Product> list(int pageNum,int pageSize);
    Optional<Product> get(int id);

    Optional<Integer> add(Product product);

    Optional<Integer> delete(int id);

    Optional<Integer> update(Product product);

    Optional<Integer> updateStatus(Product product);
    Optional<Integer> checkExistByName(String name);


}
