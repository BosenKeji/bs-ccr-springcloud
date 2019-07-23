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
    public Optional<Integer> add(Product product) {

        return Optional.ofNullable(productMapper.insert(product));
       // return flag==1?true:false;
    }

    @Override
    public Optional<Integer> delete(int id) {

        return Optional.ofNullable(productMapper.deleteByPrimaryKey(id));
        //return flag==1?true:false;
    }

    @Override
    public Optional<Integer> update(Product product) {
         return Optional.ofNullable(this.productMapper.updateByPrimaryKeySelective(product));
    }

    @Override
    public Optional<Integer> updateStatus(Product product) {

        return Optional.ofNullable(this.productMapper.updateByPrimaryKeySelective(product));
    }
}
