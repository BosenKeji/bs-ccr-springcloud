package cn.bosenkeji.service;

import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/16 14:54
 */
public interface TradePlatformCoinPairService {


    List<TradePlatformCoinPair> list();

    List<TradePlatformCoinPair> listByTradePlatformId(Integer tradePlatformId);

    /**
     * @param pageNum
     * @param pageSize
     * @return PageInfo
     */
    PageInfo listByPage(int pageNum, int pageSize);

   TradePlatformCoinPair get(int id);

    Optional<Integer> add(TradePlatformCoinPair tradePlatformCoinPair);

    Optional<Integer> update(TradePlatformCoinPair tradePlatformCoinPair);


    Optional<Integer> delete(int id);

    Optional<Integer> deleteByTradePlatformIdAndCoinPairId(int tradePlatformId, int coinPairId);

    Optional<Integer> checkByTradePlatformIdAndCoinPairId(int tradePlatformId, int coinPairId);

    Optional<Integer> checkById(int id);
}
