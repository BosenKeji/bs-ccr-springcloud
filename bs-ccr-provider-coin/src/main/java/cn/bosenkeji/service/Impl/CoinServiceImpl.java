package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.CoinMapper;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.coin.Coin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * add cache by xivin
 * 单表
 */

/**
 * @ClassName CoinServiceImpl
 * @Description 货币实现类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@Service
@CacheConfig(cacheNames = "ccr:coin")
public class CoinServiceImpl implements CoinService {

    @Resource
    private CoinMapper coinMapper;

    @Override
    public List<Coin> list() {
        return coinMapper.findAll();
    }

    @Cacheable(value = RedisInterface.COIN_LIST_KEY,key = "#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Override
    public Optional<Integer> checkExistByName(String name) {
        return Optional.ofNullable(coinMapper.checkExistByName(name));
    }

    @Cacheable(value = RedisInterface.COIN_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public Coin get(int id) {
        return coinMapper.selectByPrimaryKey(id);
    }

    @Override
    public Coin getByName(String name) {
        return this.coinMapper.selectByName(name);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> add(Coin coin) {
        this.coinMapper.insert(coin);
        return Optional.of(coin.getId());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_ID_KEY,key = "#coin.id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> update(Coin coin) {
        return Optional.ofNullable(coinMapper.updateByPrimaryKeySelective(coin));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_ID_KEY,key = "#id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(coinMapper.deleteByPrimaryKey(id));
    }
}
