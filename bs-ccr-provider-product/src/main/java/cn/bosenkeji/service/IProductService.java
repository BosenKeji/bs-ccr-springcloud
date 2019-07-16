package cn.bosenkeji.service;

import cn.bosenkeji.vo.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:37
 */
public interface IProductService {

    PageInfo<Product> list(int pageNum,int pageSize);
    Optional<Product> get(int id);

    boolean add(Product product);

    boolean delete(int id);

    boolean update(Product product);

    boolean updateStatus(int id, int status);

}
