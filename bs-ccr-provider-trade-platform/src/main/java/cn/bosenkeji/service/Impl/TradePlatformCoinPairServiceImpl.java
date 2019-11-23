package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.TradePlatformCoinPairMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.TradePlatformCoinPairService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * add cache by xivin
 * 单表
 */

/**
 * @Author CAJR
 * @create 2019/7/16 14:56
 */
@Service
public class TradePlatformCoinPairServiceImpl implements TradePlatformCoinPairService {
    @Resource
    TradePlatformCoinPairMapper tradePlatformCoinPairMapper;

    @Resource
    TradePlatformService tradePlatformService;

    @Resource
    ICoinPairClientService iCoinPairClientService;

    @Override
    public List<TradePlatformCoinPair> list() {
        return tradePlatformCoinPairMapper.findAll();
    }

    @Override
    public List<TradePlatformCoinPair> listByTradePlatformId(Integer tradePlatformId) {
        return this.tradePlatformCoinPairMapper.findAllByTradePlatformId(tradePlatformId);
    }

    @Cacheable(value = RedisInterface.TRADE_PLATFORM_COIN_LIST_KEY,key = "#pageNum+'-'+#pageSizeCommon")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Cacheable(value = RedisInterface.TRADE_PLATFORM_COIN_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public TradePlatformCoinPair get(int id) {
        return tradePlatformCoinPairMapper.selectByPrimaryKey(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> add(CoinPair coinPair , String tradePlatformName) {
        TradePlatformCoinPair tradePlatformCoinPair = new TradePlatformCoinPair();
        TradePlatform tradePlatform = this.tradePlatformService.getByName(tradePlatformName);
        if (tradePlatform.getId() == 0 || tradePlatform == null || coinPair.getName() == null){
            return Optional.of(-1);
        }
        tradePlatformCoinPair.setTradePlatformId(tradePlatform.getId());
        int coinPairId = (int) this.iCoinPairClientService.addCoinPair(coinPair).getData();
        coinPairId = Math.abs(coinPairId);
        if (coinPairId <= 0){
            return Optional.of(-1);
        }

        tradePlatformCoinPair.setCoinPairId(coinPairId);
        tradePlatformCoinPair.setStatus(1);
        tradePlatformCoinPair.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformCoinPair.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        if (this.tradePlatformCoinPairMapper.checkExistByTradePlatformIdAndCoinPairId(tradePlatformCoinPair.getTradePlatformId(),tradePlatformCoinPair.getCoinPairId()) >= 1){
            return  Optional.of(-1);
        }

        return Optional.of(tradePlatformCoinPairMapper.insertSelective(tradePlatformCoinPair));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_ID_KEY,key = "#tradePlatformCoinPair.id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> update(TradePlatformCoinPair tradePlatformCoinPair) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.updateByPrimaryKeySelective(tradePlatformCoinPair));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_ID_KEY,key = "#id",condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.deleteByPrimaryKey(id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_ID_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result > 0"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_ID_KEY,allEntries = true,condition = "#result != null && #result > 0")
            }
    )
    @Override
    public Optional<Integer> deleteByTradePlatformIdAndCoinPairId(int tradePlatformId, int coinPairId) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.deleteByTradePlatformIdAndCoinPairId(tradePlatformId, coinPairId));
    }

    @Override
    public Optional<Integer> checkByTradePlatformIdAndCoinPairId(int tradePlatformId, int coinPairId) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.checkExistByTradePlatformIdAndCoinPairId(tradePlatformId, coinPairId));
    }

    @Override
    public Optional<Integer> checkById(int id) {
        return Optional.ofNullable(tradePlatformCoinPairMapper.checkExistById(id));
    }


}
