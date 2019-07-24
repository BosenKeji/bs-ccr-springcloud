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
    List<CoinSort> list();

    PageInfo listByPage(int pageNum, int pageSize);

    PageInfo listByTradePlatformId(int tradePlatformId,int pageNum, int pageSize);

    Optional<CoinSort> get(int id);

    Optional<Integer> add(CoinSort coinSort);

    Optional<Integer> update(CoinSort coinSort);

    Optional<Integer> delete(int coinId);
}
