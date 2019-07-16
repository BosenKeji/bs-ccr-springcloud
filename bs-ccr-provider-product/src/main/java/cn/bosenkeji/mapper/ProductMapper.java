package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.Product;

import java.util.List;

public interface ProductMapper {
    boolean deleteByPrimaryKey(Integer id);

    boolean insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    boolean updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> findAll();
}