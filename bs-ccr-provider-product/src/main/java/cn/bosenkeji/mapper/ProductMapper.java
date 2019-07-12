package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    long countByExample(ProductExample example);

    boolean deleteByExample(ProductExample example);

    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Product record);

    boolean insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    boolean updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

    boolean updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

    boolean updateByPrimaryKeySelective(Product record);

    boolean updateByPrimaryKey(Product record);
}