package cn.bosenkeji.service;

import cn.bosenkeji.vo.ProductCombo;

import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:39
 */
public interface IProductComboService {
    List<ProductCombo> list();
    ProductCombo get(int id);
    boolean add(ProductCombo productCombo);
    boolean update(ProductCombo productCombo);
    boolean delete(int id);
}
