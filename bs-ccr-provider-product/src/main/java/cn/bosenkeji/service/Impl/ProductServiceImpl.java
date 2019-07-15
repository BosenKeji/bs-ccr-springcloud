package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductMapper;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.Product;
import cn.bosenkeji.vo.ProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:41
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> list() {
        return this.productMapper.selectByExample(new ProductExample());
    }

    public Product get(int id) {
        return this.productMapper.selectByPrimaryKey(id);
    }
    @Override
    public boolean add(Product product) {

        return productMapper.insert(product);
       // return flag==1?true:false;
    }

    @Override
    public boolean delete(int id) {

        return productMapper.deleteByPrimaryKey(id);
        //return flag==1?true:false;
    }

    @Override
    public boolean update(Product product) {
         return this.productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public boolean updateStatus(int id, int status) {
        Product product=new Product();
        product.setId(id);
        product.setStatus(status);
        return this.productMapper.updateByPrimaryKeySelective(product);
    }
}
