package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.CoinSortMapper;
import cn.bosenkeji.service.CoinSortService;
import cn.bosenkeji.vo.coin.CoinSort;
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
 * 与coin 表有关联
 */

/**
 * @Author CAJR
 * @create 2019/7/10 18:21
 */
@Service
@CacheConfig(cacheNames = "ccr:coinSort")
public class CoinSortServiceImpl implements CoinSortService {
    @Resource
    CoinSortMapper coinSortMapper;

    /**
     *
     * cache 与coin货币表有关联查询
     *
     */
    @Cacheable(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,key = "#tradePlatformId+'-'+#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByTradePlatformId(int tradePlatformId,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo(this.coinSortMapper.findAllByTradePlatformId(tradePlatformId));
    }


    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> add(CoinSort coinSort) {
        return Optional.ofNullable(coinSortMapper.insertSelective(coinSort));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> update(CoinSort coinSort) {
        return  Optional.ofNullable(coinSortMapper.updateByPrimaryKeySelective(coinSort) );
    }

    @Override
    public CoinSort get(int coinSortId) {
        return this.coinSortMapper.selectByPrimaryKey(coinSortId);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> delete(int tradePlatform,int coinId) {
        return  Optional.ofNullable(coinSortMapper.deleteByTradePlatformIdAndCoinId(tradePlatform,coinId));
    }

    @Override
    public Optional<Integer> checkByTradePlatformIdAndCoinId(int tradePlatformId, int coinId) {
        return Optional.ofNullable(this.coinSortMapper.checkByTradePlatformIdAndCoinId(tradePlatformId, coinId));
    }
}
