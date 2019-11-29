package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.CoinPairCoinMapper;
import cn.bosenkeji.service.CoinPairCoinService;
import cn.bosenkeji.service.CoinPairService;
import cn.bosenkeji.service.CoinService;
import cn.bosenkeji.vo.coin.Coin;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import cn.bosenkeji.vo.coin.CoinPairCoinResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * add cache by xivin
 * 单表查询
 */

/**
 * @Author CAJR
 * @create 2019/7/10 18:11
 */
@CacheConfig(cacheNames = "ccr:coinPairCoin")
@Service
public class CoinPairCoinServiceImpl implements CoinPairCoinService {

    @Resource
    CoinPairCoinMapper coinPairCoinMapper;

    @Resource
    CoinPairService coinPairService;

    @Resource
    CoinService coinService;

    @Override
    public List<CoinPairCoin> list() {
        return coinPairCoinMapper.findAll();
    }

    @Cacheable(value = RedisInterface.COIN_PAIR_COIN_LIST_CID_KEY,key = "#coinId",unless = "#result != null")
    @Override
    public List<CoinPairCoin> listByCoinId(int coinId) {
        return this.coinPairCoinMapper.findAllByCoinId(coinId);
    }

    @Cacheable(value = RedisInterface.COIN_PAIR_COIN_LIST_KEY,key = "#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(list());
    }

    @Cacheable(value = RedisInterface.COIN_PAIR_COIN_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public CoinPairCoin get(int id) {
        return coinPairCoinMapper.selectByPrimaryKey(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result >0 "),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> add(CoinPairCoin coinPairCoin) {
        return Optional.of(coinPairCoinMapper.insertSelective(coinPairCoin));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_COIN_ID_KEY,key = "#coinPairCoin.id",condition = "#result != null && #result >0 "),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result >0 "),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> update(CoinPairCoin coinPairCoin) {
        return Optional.of(coinPairCoinMapper.updateByPrimaryKeySelective(coinPairCoin));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.COIN_PAIR_COIN_ID_KEY,allEntries = true,condition = "#result != null && #result >0 "),
                    @CacheEvict(value = RedisInterface.COIN_PAIR_COIN_LIST_KEY,allEntries = true,condition = "#result != null && #result >0 "),
                    @CacheEvict(value = RedisInterface.COIN_SORT_LIST_TPID_KEY,allEntries = true,condition = "#result != null && #result >0 ")
            }
    )
    @Override
    public Optional<Integer> delete(int coinId, int coinPairId) {
        return Optional.of(coinPairCoinMapper.deleteByCoinIdAndCoinPairId(coinId, coinPairId));
    }

    @Override
    public Optional<Integer> checkByCoinIdAndCoinPairId(int coinId, int coinPairId) {
        return Optional.ofNullable(this.coinPairCoinMapper.checkByCoinIdAndCoinPairId(coinId, coinPairId));
    }

    @Override
    public CoinPairCoinResult findBaseCoin(String coinPairName) {
        coinPairName = coinPairName.toLowerCase();
        if (coinPairName.contains("-")){
            coinPairName = coinPairName.replaceAll("-","").trim();
        }

        CoinPairCoinResult result = new CoinPairCoinResult();
        CoinPair coinPair = this.coinPairService.getByName(coinPairName);
        if (coinPair == null){
            return result;
        }
        result.setCoinPairName(coinPair.getName());
        List<CoinPairCoin> coinPairCoins = this.coinPairCoinMapper.findAllByCoinPairId(coinPair.getId());
        if (!CollectionUtils.isEmpty(coinPairCoins)){
            for (CoinPairCoin coinPairCoin : coinPairCoins) {
                String coinName = "";
                Coin coin = this.coinService.get(coinPairCoin.getCoinId());
                if (coin != null){
                    coinName = coin.getName();
                }
                int coinNameLength = coinName.length();
                int coinPairNameLength = coinPairName.length();
                if (coinPairName.lastIndexOf(coinName)+coinNameLength != coinPairNameLength){
                    result.setBaseCoinName(coinName);
                }else {
                    result.setValuationCoinName(coinName);
                }
            }
        }
        if (result.getBaseCoinName() == null && result.getValuationCoinName() != null){
            result.setBaseCoinName(coinPairName.replace(result.getValuationCoinName(),"").trim());
        }

        return result;
    }


}
