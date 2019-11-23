package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.TradePlatformMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.service.TradePlatformCoinPairService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * add cache by xivin
 * 关联 coinPair 货币对表
 */

/**
 * @Author CAJR
 * @create 2019/7/15 18:02
 */
@Service
@CacheConfig(cacheNames = "ccr:tradePlatform")
public class TradePlatformServiceImpl implements TradePlatformService {

    @Resource
    TradePlatformMapper tradePlatformMapper;

    @Resource
    TradePlatformCoinPairService tradePlatformCoinPairService;

    @Resource
    ICoinPairClientService iCoinPairClientService;



    @Override
    public List<TradePlatform> list() {
        return tradePlatformMapper.findAll();
    }

    @Cacheable(value = RedisInterface.TRADE_PLATFORM_LIST_KEY,key = "#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<TradePlatform> tradePlatforms = list();
        return new PageInfo<>(tradePlatforms);
    }


    private void fill(TradePlatform tradePlatform, List<Integer> coinPairIds) {
        if (!coinPairIds.isEmpty()){
            List<CoinPair> coinPairs = this.iCoinPairClientService.findSection(coinPairIds);
            tradePlatform.setCoinPairs(coinPairs);
        }
    }


    /**
     * 与 coinPairs 关联查询  先与tradePlatformCoinPair关联
     * @param id
     * @return
     */
    @Cacheable(value = RedisInterface.TRADE_PLATFORM_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public TradePlatform get(int id) {
        TradePlatform tradePlatform = tradePlatformMapper.selectByPrimaryKey(id);
        if (tradePlatform != null){
            List<TradePlatformCoinPair> tradePlatformCoinPairs = tradePlatform.getTradePlatformCoinPairs();
            List<Integer> coinPairIds = new ArrayList<>();
            if (!tradePlatformCoinPairs.isEmpty()){
                for (TradePlatformCoinPair t: tradePlatformCoinPairs) {
                    coinPairIds.add(t.getCoinPairId());
                }
                fill(tradePlatform,coinPairIds);
            }
        }

        return tradePlatform;
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> add(TradePlatform tradePlatform) {
        return Optional.ofNullable(tradePlatformMapper.insertSelective(tradePlatform));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,key = "#tradePlatform.id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> update(TradePlatform tradePlatform) {
        return Optional.ofNullable(tradePlatformMapper.updateByPrimaryKeySelective(tradePlatform));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,key = "#id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),

                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(tradePlatformMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByName(String name) {
        return Optional.ofNullable(this.tradePlatformMapper.checkExistByName(name));
    }

    @Override
    public TradePlatform getByName(String name) {
        if (name.isEmpty()){
            return new TradePlatform();
        }
        return this.tradePlatformMapper.selectByName(name);
    }
}
