package cn.bosenkeji.service;

import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:39
 */
public interface IProductComboService {
    PageInfo<ProductCombo> list(int pageNum,int pageSize);
    PageInfo<ProductCombo> listByStatus(int pageNum,int pageSize,int status);
    Optional<ProductCombo> get(int id);
    boolean add(ProductCombo productCombo);
    boolean update(ProductCombo productCombo);
    boolean delete(int id);
}
