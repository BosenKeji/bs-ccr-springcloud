package cn.bosenkeji.service;

import cn.bosenkeji.vo.CoinPair;
import cn.bosenkeji.vo.CoinSort;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:09
 */

public interface CoinSortService {
    List<CoinSort> list();

    Optional<CoinSort> get(int id);

    boolean add(CoinSort coinSort);

    boolean update(CoinSort coinSort);

    boolean delete(int id);
}
