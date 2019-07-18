package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.ProductCombo;

import java.util.List;

public interface ProductComboMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(ProductCombo record);

    int insertSelective(ProductCombo record);

    ProductCombo selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(ProductCombo record);

    int updateByPrimaryKey(ProductCombo record);

    List<ProductCombo> findAll();
    List<ProductCombo> findAllByStatus(Integer status);

    List<ProductCombo> findByProductId(Integer productId);

    int selectTimeByPrimaryKey(Integer id);

}