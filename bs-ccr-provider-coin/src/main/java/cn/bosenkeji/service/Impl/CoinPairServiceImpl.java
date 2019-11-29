package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.CoinPairMapper;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.vo.coin.CoinPair;
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
 * 纯单表操作
 */

/**
 * @Author CAJR
 * @create 2019/7/10 18:12
 */
@Service
@CacheConfig(cacheNames = "ccr:coinPair")
public class CoinPairServiceImpl implements CoinPairService {

    @Resource
    CoinPairMapper coinPairMapper;

    @Override
    public List<CoinPair> list() {
        return coinPairMapper.findAll();
    }

    @Override
    public List<CoinPair> listSection(List<Integer> ids) {
        return this.coinPairMapper.findSectionByIds(ids);
    }

    @Cacheable(value = RedisInterface.COIN_PAIR_LIST_KEY,key = "#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Cacheable(value = RedisInterface.COIN_PAIR_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public CoinPair get(int id) {
        return coinPairMapper.selectByPrimaryKey(id);
    }

    @Override
    public CoinPair getByName(String name) {
        return coinPairMapper.selectByName(name);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> add(CoinPair coinPair) {
        String coinPairName = coinPair.getName();
        if (coinPairName.contains("-")){
            coinPairName = coinPairName.replaceAll("-","").trim();
            coinPair.setName(coinPairName.toLowerCase());
        }

        if (this.coinPairMapper.checkExistByName(coinPairName) >= 1){
            CoinPair coinPair1 = this.coinPairMapper.selectByName(coinPairName);
            int coinPairId = coinPair1.getId();
            return Optional.of(0-coinPairId);
        }
        coinPairMapper.insertSelective(coinPair);
        return Optional.of(coinPair.getId());
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_ID_KEY,key = "#coinPair.id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_LIST_IDS_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> update(CoinPair coinPair) {
        return Optional.of(coinPairMapper.updateByPrimaryKeySelective(coinPair));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_ID_KEY,key = "#id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_LIST_IDS_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,allEntries = true,condition = "#result != null && #result > 0"),

                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_ID_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_CHOICE_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(coinPairMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByName(String name) {
        return Optional.ofNullable(this.coinPairMapper.checkExistByName(name));
    }
}
