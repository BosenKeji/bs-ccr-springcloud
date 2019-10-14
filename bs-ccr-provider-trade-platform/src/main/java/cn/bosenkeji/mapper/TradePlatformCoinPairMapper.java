package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;

import java.util.List;

/**
 * @Author CAJR
 * @create 2019/7/16 14:53
 */

public interface TradePlatformCoinPairMapper {
    int deleteByPrimaryKey(int id);

    int insert(TradePlatformCoinPair record);

    int insertSelective(TradePlatformCoinPair record);

    TradePlatformCoinPair selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(TradePlatformCoinPair record);

    int updateByPrimaryKey(TradePlatformCoinPair record);

    List<TradePlatformCoinPair> findAll();

    List<TradePlatformCoinPair> findAllByTradePlatformId(Integer tradePlatformId);

    int deleteByTradePlatformIdAndCoinPairId (int tradePlatformId, int coinPairId);

    int checkExistByTradePlatformIdAndCoinPairId (int tradePlatformId, int coinPairId);
    
    int checkExistById (int id);
}