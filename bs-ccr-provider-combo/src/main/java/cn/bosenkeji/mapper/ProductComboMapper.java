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

    int selectTimeByPrimaryKey(Integer id);

}