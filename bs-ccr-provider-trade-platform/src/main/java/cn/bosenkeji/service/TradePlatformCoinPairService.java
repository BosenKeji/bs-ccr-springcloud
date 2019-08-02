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

    /**
     * @param pageNum
     * @param pageSize
     * @return PageInfo
     */
    PageInfo listByPage(int pageNum, int pageSize);

    Optional<TradePlatformCoinPair> get(int id);

    Optional<Integer> add(TradePlatformCoinPair tradePlatformCoinPair);

    Optional<Integer> update(TradePlatformCoinPair tradePlatformCoinPair);

    /**
     *
     * @param tradePlatformId
     * @param coinPairId
     * @return
     */
    Optional<Integer> delete(int tradePlatformId, int coinPairId);


    Optional<Integer> checkByTradePlatformIdAndCoinPairId(int tradePlatformId, int coinPairId);
}
