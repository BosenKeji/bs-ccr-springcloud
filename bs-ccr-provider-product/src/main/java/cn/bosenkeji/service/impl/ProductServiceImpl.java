package cn.bosenkeji.service.impl;

import cn.bosenkeji.mapper.ProductMapper;
import cn.bosenkeji.service.IProductService;
import cn.bosenkeji.vo.Product;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @create 2019-07-11 11:41
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public PageInfo<Product> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<Product>(this.productMapper.findAll());
    }
    @Override
    public Optional<Product> get(int id) {
        return Optional.ofNullable(this.productMapper.selectByPrimaryKey(id));
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
