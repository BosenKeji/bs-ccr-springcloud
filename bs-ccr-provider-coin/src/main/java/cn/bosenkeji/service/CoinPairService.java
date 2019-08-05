package cn.bosenkeji.service;

import cn.bosenkeji.vo.coin.CoinPair;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:08
 */
public interface CoinPairService {
    List<CoinPair> list();

    PageInfo listByPage(int pageNum, int pageSize);

    Optional<CoinPair> get(int id);

    Optional<CoinPair> getByName(String name);

    Optional<Integer> add(CoinPair coinPair);

    Optional<Integer> update(CoinPair coinPair);

    Optional<Integer> delete(int id);

    Optional<Integer> checkExistByName(String name);
}
