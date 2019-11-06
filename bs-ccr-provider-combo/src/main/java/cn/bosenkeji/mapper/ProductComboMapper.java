package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.combo.ProductCombo;
import org.apache.ibatis.annotations.Param;

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
    List<ProductCombo> findByProductIdAndStatus(Integer productId, Integer status);

    int selectTimeByPrimaryKey(Integer id);
    int checkExistByNameAndProductId(String name, Integer productId);

    int checkExistByProductId(Integer productId);

    int selectProductIdByPrimaryKey(Integer id);

    List<ProductCombo> selectProductComboByIds(@Param("ids") List ids);

}