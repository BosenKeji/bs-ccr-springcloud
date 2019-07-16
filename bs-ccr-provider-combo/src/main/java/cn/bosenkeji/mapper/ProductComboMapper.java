package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.ProductCombo;
import cn.bosenkeji.vo.ProductComboExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductComboMapper {
    long countByExample(ProductComboExample example);

    boolean deleteByExample(ProductComboExample example);

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(ProductCombo record);

    boolean insertSelective(ProductCombo record);

    List<ProductCombo> selectByExample(ProductComboExample example);

    ProductCombo selectByPrimaryKey(Integer id);

    boolean updateByExampleSelective(@Param("record") ProductCombo record, @Param("example") ProductComboExample example);

    int updateByExample(@Param("record") ProductCombo record, @Param("example") ProductComboExample example);

    boolean updateByPrimaryKeySelective(ProductCombo record);

    boolean updateByPrimaryKey(ProductCombo record);
}