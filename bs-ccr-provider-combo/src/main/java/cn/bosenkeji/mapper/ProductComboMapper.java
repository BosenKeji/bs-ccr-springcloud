package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.ProductCombo;

import java.util.List;

public interface ProductComboMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductCombo record);

    int insertSelective(ProductCombo record);

    ProductCombo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductCombo record);

    int updateByPrimaryKey(ProductCombo record);

    List<ProductCombo> findAll();
    List<ProductCombo> findAllByStatus(Integer status);

    List<ProductCombo> findByProductId(Integer productId);
    List<ProductCombo> findByProductIdAndStatus(Integer productId,Integer status);

    int selectTimeByPrimaryKey(Integer id);

}