package cn.bosenkeji.service;

import cn.bosenkeji.vo.Product;

import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:37
 */
public interface IProductService {

    List<Product> list();
    Product get(int id);

    boolean add(Product product);

    boolean delete(int id);

    boolean update(Product product);

}
