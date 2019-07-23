package cn.bosenkeji.service;

import cn.bosenkeji.vo.tradeplateform.TradePlatformCoinPair;
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

    boolean add(TradePlatformCoinPair tradePlatformCoinPair);

    boolean update(TradePlatformCoinPair tradePlatformCoinPair);

    /**
     *
     * @param tradePlatformId
     * @param coinPairId
     * @return
     */
    boolean delete(int tradePlatformId, int coinPairId);
}
