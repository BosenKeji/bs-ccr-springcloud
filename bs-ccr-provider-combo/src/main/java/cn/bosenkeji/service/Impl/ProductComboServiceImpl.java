package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.ProductCombo;
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
public class ProductComboServiceImpl implements IProductComboService {

    @Resource
    private ProductComboMapper productComboMapper;

    @Override
    public PageInfo<ProductCombo> list(int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<ProductCombo>(this.productComboMapper.findAll());
    }

    @Override
    public PageInfo<ProductCombo> listByProductId(int pageNum, int pageSize, int productId) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(productComboMapper.findByProductId(productId));
    }

    @Override
    public PageInfo<ProductCombo> listByProductIdAndStauts(int pageNum, int pageSize, int productId,int status) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(productComboMapper.findByProductIdAndStatus(productId,status));
    }

    @Override
    public PageInfo<ProductCombo> listByStatus(int pageNum,int pageSize,int status) {

        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<ProductCombo>(this.productComboMapper.findAllByStatus(status));
    }

    @Override
    public Optional<ProductCombo> get(int id) {
        return Optional.ofNullable(this.productComboMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(ProductCombo productCombo) {
        return this.productComboMapper.insert(productCombo);
    }

    @Override
    public boolean update(ProductCombo productCombo) {

        return this.productComboMapper.updateByPrimaryKeySelective(productCombo);
    }

    @Override
    public boolean delete(int id) {
        return this.productComboMapper.deleteByPrimaryKey(id);
    }
}
