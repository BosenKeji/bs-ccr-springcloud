package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.product.Product;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> findAll();

    int checkExistByName(String name);

    @MapKey("id")
    Map<Integer,Product> selectByPrimaryKeys(List ids);

    List<Product> selectByStatus(Integer status);

    int checkExistByNameAndVersionName(String name,String versionName);

    int checkExistById(Integer id);
}