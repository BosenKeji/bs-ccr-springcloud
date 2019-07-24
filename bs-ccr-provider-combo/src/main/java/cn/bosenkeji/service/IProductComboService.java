package cn.bosenkeji.service;

import cn.bosenkeji.vo.combo.ProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:39
 */
public interface IProductComboService {
    PageInfo<ProductCombo> list(int pageNum,int pageSize);
    PageInfo<ProductCombo> listByProductId(int pageNum,int pageSize,int productId);
    PageInfo<ProductCombo> listByProductIdAndStauts(int pageNum,int pageSize,int productId,int status);
    PageInfo<ProductCombo> listByStatus(int pageNum,int pageSize,int status);
    Optional<ProductCombo> get(int id);
    Optional<Integer> add(ProductCombo productCombo);
    Optional<Integer> update(ProductCombo productCombo);
    Optional<Integer> delete(int id);
    Optional<Integer> checkExistByName(String name);
}
