package cn.bosenkeji.service;

import cn.bosenkeji.vo.coin.CoinSort;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:09
 */

public interface CoinSortService {

    PageInfo listByTradePlatformId(int tradePlatformId,int pageNum, int pageSize);

    Optional<Integer> add(CoinSort coinSort);

    Optional<Integer> update(CoinSort coinSort);

    CoinSort get(int coinSortId);

    Optional<Integer> delete(int tradePlatform,int coinId);

    Optional<Integer> checkByTradePlatformIdAndCoinId(int tradePlatformId,int coinId);
}
