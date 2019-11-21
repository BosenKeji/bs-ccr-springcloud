package cn.bosenkeji.service.Impl;

import cn.bosenkeji.annotation.cache.BatchCacheRemove;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.service.IProductComboService;
import cn.bosenkeji.vo.combo.ProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    public PageInfo<ProductCombo> list(int pageNum,int pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<ProductCombo>(this.productComboMapper.findAll());
    }

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_LIST_PID_KEY,key = "#productId+'-'+#pageNum+'-'+#pageSize")
    @Override
    public PageInfo<ProductCombo> listByProductId(int pageNum, int pageSize, int productId) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(productComboMapper.findByProductId(productId));
    }

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_PAGE_KEY,key = "#productId+'-'+#status+'-'+#pageNum+'-'+#pageSize")
    @Override
    public PageInfo<ProductCombo> listByProductIdAndStatusWithPage(int pageNum, int pageSize, int productId,int status) {

        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo<>(productComboMapper.findByProductIdAndStatus(productId,status));
    }

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,key = "#productId +'-'+ #status")
    @Override
    public List<ProductCombo> listByProductIdAndStatus( int productId,int status) {

        return productComboMapper.findByProductIdAndStatus(productId,status);
    }

    @Override
    public PageInfo<ProductCombo> listByStatus(int pageNum,int pageSize,int status) {

        PageHelper.startPage(pageNum,pageSize);

        return new PageInfo<ProductCombo>(this.productComboMapper.findAllByStatus(status));
    }

    @Cacheable(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#id",unless = "#result==null")
    @Override
    public ProductCombo get(int id) {
        return this.productComboMapper.selectByPrimaryKey(id);
    }


    @BatchCacheRemove({"ccr:productCombo:listByPidAndStatus::+#productCombo.productId+-","ccr:productCombo:listByPidAndStatusWithPage::+#productCombo.productId+-",
            "ccr:productCombo:listByPid::+#productCombo.productId+-"})
    @Override
    public int add(ProductCombo productCombo) {
        return this.productComboMapper.insertSelective(productCombo);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#productCombo.id"),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_PAGE_KEY,allEntries = true)
            }
    )
    @Override
    public int update(ProductCombo productCombo) {

        return this.productComboMapper.updateByPrimaryKeySelective(productCombo);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_PID_STATUS_PAGE_KEY,allEntries = true)
            }
    )
    @Override
    public int delete(int id) {
        return this.productComboMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int checkExistByNameAndProductId(String name,int productId) {
        return productComboMapper.checkExistByNameAndProductId(name,productId);
    }

    @Override
    public List<ProductCombo> getByIds(List<Integer> ids) {
        return productComboMapper.selectProductComboByIds(ids);
    }

    @Override
    public int checkExistByProductId(int productId) {
        return productComboMapper.checkExistByProductId(productId);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.PRODUCT_COMBO_LIST_KEY,allEntries = true)
            }
    )
    @BatchCacheRemove({"ccr:productCombo:listByPidAndStatus::+#productCombo.productId+-","ccr:productCombo:listByPidAndStatusWithPage::+#productCombo.productId+-",
            "ccr:productCombo:listByPid::+#productCombo.productId+-"})
    @Override
    public int addBySelective(ProductCombo productCombo) {
        return productComboMapper.insertSelective(productCombo);
    }
}
