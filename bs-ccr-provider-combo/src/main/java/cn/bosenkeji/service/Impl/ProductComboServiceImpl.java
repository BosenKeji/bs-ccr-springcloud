package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.ProductCombo;
import cn.bosenkeji.vo.ProductComboExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xivin
 * @create 2019-07-11 11:41
 */
@Service
public class ProductComboServiceImpl implements IProductComboService {

    @Resource
    private ProductComboMapper productComboMapper;

    @Override
    public List<ProductCombo> list() {
        return this.productComboMapper.selectByExample(new ProductComboExample());
    }

    @Override
    public ProductCombo get(int id) {
        return this.productComboMapper.selectByPrimaryKey(id);
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
