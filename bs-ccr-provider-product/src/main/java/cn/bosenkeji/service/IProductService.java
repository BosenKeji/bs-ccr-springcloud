package cn.bosenkeji.service;

import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @create 2019-07-11 11:37
 */
public interface IProductService {

    PageInfo<Product> list(int pageNum, int pageSize);
    Product get(int id);

    int add(Product product);

    int delete(int id);

    int update(Product product);

    int updateStatus(Product product);
    int checkExistByName(String name);

    Map<Integer,Product> selectByPrimaryKeys(List ids);

    PageInfo<Product> selectByStatus(int status,int pageNum,int pageSize);

    int checkExistByNameAndVersionName(String name,String versionName);
    int checkExistById(int id);


}
