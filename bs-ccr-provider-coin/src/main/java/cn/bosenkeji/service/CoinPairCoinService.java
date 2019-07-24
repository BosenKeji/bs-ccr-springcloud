package cn.bosenkeji.service;

import cn.bosenkeji.vo.coin.CoinPairCoin;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/10 18:08
 */
public interface CoinPairCoinService {
    List<CoinPairCoin> list();

    PageInfo listByPage(int pageNum, int pageSize);

    Optional<CoinPairCoin> get(int id);

    Optional<Integer> add(CoinPairCoin coinPairCoin);

    Optional<Integer> update(CoinPairCoin coinPairCoin);

    Optional<Integer> delete(int coinId,int coinPairId);
}
