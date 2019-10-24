package cn.bosenkeji.service.impl;


import cn.bosenkeji.mapper.ProductMapper;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.product.Product;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @create 2019-07-11 11:41
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

//    @Override
    public PageInfo<Product> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Product>(this.productMapper.findAll());
    }
    @Override
    public Product get(int id) {
        return this.productMapper.selectByPrimaryKey(id);
    }
    @Override
    public int add(Product product) {

        return productMapper.insertSelective(product);
        // return flag==1?true:false;
    }

    @Override
    public int delete(int id) {

        return productMapper.deleteByPrimaryKey(id);
        //return flag==1?true:false;
    }

    @Override
    public int update(Product product) {
        return this.productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int updateStatus(Product product) {

        return this.productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public int checkExistByName(String name) {
        return productMapper.checkExistByName(name);
    }

    @Override
    public Map<Integer, Product> selectByPrimaryKeys(List ids) {
        return this.productMapper.selectByPrimaryKeys(ids);
    }

    @Override
    public PageInfo<Product> selectByStatus(int status,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(productMapper.selectByStatus(status));
    }

    @Override
    public int checkExistByNameAndVersionName(String name, String versionName) {
        return productMapper.checkExistByNameAndVersionName(name,versionName);
    }

    @Override
    public int checkExistById(int id) {
        return productMapper.checkExistById(id);
    }
}
